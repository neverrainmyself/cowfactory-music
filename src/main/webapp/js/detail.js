/*
 jQuery Setup
 ************************************************************************/
jQuery.ajaxSetup({
    cache: false
})

/*
 ArticleAnimator Object
 ************************************************************************/
var ArticleAnimator = ArticleAnimator || {
    canScroll: true,
    initialLoad: true,
    animationDuration: 500,
    postCount: 5,
    currentPostIndex: 1,
    postCache: {},
    pageTemplate: null
};

ArticleAnimator.load = function () {
    this.currentPostIndex = getURLIndex();
    this.makeSelections();

    $body.append(this.$current)
    $body.append(this.$next)


    if ($('#headerimage').length > 0) {
        $('.detailpage.current .big-image').css({ backgroundImage: "url(/loadimage?guid=" + $('#headerimage').val() + ")" });
    } else {
        $('.detailpage.current .big-image').css({ background: "#ccc"});
    }
    var self = this;

    self.createPost({ type: 'next' }, function () {

        /* Selections. */
        self.refreshCurrentAndNextSelection();


        /* Bind to some events. */
        self.bindGotoNextClick();
        self.bindPopstate();
        self.bindWindowScroll();

    })
}

ArticleAnimator.makeSelections = function () {
    this.$page = $('.detailpage');
    this.pageTemplate = elementToTemplate(this.$page.clone());
    this.$current = this.currentElementClone();
    this.$next = this.nextElementClone();
}

ArticleAnimator.getPost = function (index, callback) {
    callback = callback || $.noop;


    var self = this;
    $.getJSON('/next?guid=' + $('#guid').val(), function (d) {
        callback(d)
    });
}

ArticleAnimator.nextPostIndex = function (index) {
    return (index === this.postCount) ? 1 : index + 1
}

ArticleAnimator.createPost = function (opts, callback) {
    opts = opts || {};
    var self = this;
    var type = opts['type'] || 'next';


    if (opts['fromTemplate']) {
        $body.append(this.nextElementClone());
        this['$' + type] = $('.' + type)
    }


    var index = (type == 'next') ? this.nextPostIndex(this.currentPostIndex) : this.currentPostIndex;
    this.getPost(index, function (d) {
        self.contentizeElement(self['$' + type], d);

        callback && callback();
    });

}

ArticleAnimator.contentizeElement = function ($el, d) {

    if (d.music.headerImage == null) {
        $el.find('.big-image').css({ backgroundr: "#ccc" });
    } else {
        $el.find('.big-image').css({ backgroundImage: "url(/loadimage?guid=" + d.music.headerImage + ")" });
    }
    $el.find('h1.title').html(d.music.title);
    $el.find('h2.description').html(d.music.subTitle);
    $el.find('.content .text').html(d.music.content);
    $('#guid').val(d.music.guid);
    var liked = d.liked;
    if (liked) {
        $el.find('.fui-heart').parent().addClass('active');
    } else {
        $el.find('.fui-heart').parent().removeClass('active');
    }
    $el.find('.upvoters').empty();

    $el.find('.showallcommentbtn').attr('onclick', 'showComments("' + d.music.guid + '",0);');
    $el.find('.commentamount').html(d.music.comments);
    $el.find('.likedamount').html(d.music.liked);
    $el.find('.icon-share').attr('onclick', 'window.open(\'http://service.weibo.com/share/share.php?url=http://dowhatcowdo.com/detail?guid='+ d.music.guid+'&appkey=J7ySv&title=进入Cow Factory,听听@'+ d.music.user.weiboName+'在<<'+ d.music.title+'>>这首歌背后的故事&pic=dowhatcowdo.com/loadimage?guid='+ d.music.headerImage+'&ralateUid=3487680995&language=zh_cn\')');
    $.each(d.music.likedBy, function (i, e) {
        $el.find('.upvoters').append('<li class="upvoters-item likeduser{userguid}"><a href="{userprofileurl}" class="avatar-chromeless avatar-icon"><img src="{userimageurl}"></a></li>'
            .replace('{userguid}', e.guid)
            .replace('{userprofileurl}', '/profile?guid=' + e.guid)
            .replace('{userimageurl}', e.weiboImage)
        );
    });

    $('.detailpage.next .btn-group .single-music').attr('onclick', 'playOneFromList("' + d.music.guid + '",$(this))');
    $('.detailpage.next .btn-group .single-music').attr('name', d.music.title);
    $('.detailpage.next .btn-group .liked').attr('onclick', 'liked("' + d.music.guid + '",$(this))');
}

ArticleAnimator.animatePage = function (callback) {
    var self = this;
    var translationValue = this.$next.get(0).getBoundingClientRect().top;
    this.canScroll = false;

    this.$current.addClass('fade-up-out');

    this.$next.removeClass('content-hidden next')
        .addClass('easing-upward')
        .css({ "transform": "translate3d(0, -" + translationValue + "px, 0)" });

    setTimeout(function () {
        scrollTop();
        self.$next.removeClass('easing-upward')
        self.$current.remove();

        self.$next.css({ "transform": "" });
        self.$current = self.$next.addClass('current');

        self.canScroll = true;
        self.currentPostIndex = self.nextPostIndex(self.currentPostIndex);

        callback();
    }, self.animationDuration);
}

ArticleAnimator.bindGotoNextClick = function () {
    var self = this;
    var e = 'ontouchstart' in window ? 'touchstart' : 'click';

    this.$next.find('.big-image').on(e, function (e) {
        e.preventDefault();
        if (commentsShowed) {
            closeCommentsSnapper();
        }
        $(this).unbind(e);

        self.animatePage(function () {
            self.createPost({ fromTemplate: true, type: 'next' });
            self.bindGotoNextClick();
        });
    });
}

ArticleAnimator.bindPopstate = function () {
    var self = this;
    $window.on('popstate', function (e) {

        if (!history.state || self.initialLoad) {
            self.initialLoad = false;
            return;
        }

        self.currentPostIndex = history.state.index;
        self.$current.replaceWith(history.state.current);
        self.$next.replaceWith(history.state.next);

        self.refreshCurrentAndNextSelection();
        self.createPost({ type: 'next' });
        self.bindGotoNextClick();
    });
}

ArticleAnimator.bindWindowScroll = function () {
    var self = this;
    $window.on('mousewheel', function (ev) {
        if (!self.canScroll)
            ev.preventDefault()
    })
}

ArticleAnimator.refreshCurrentAndNextSelection = function () {
    this.$current = $('.detailpage.current');
    this.$next = $('.detailpage.next');
}

ArticleAnimator.nextElementClone = function () {
    return this.$page.clone().removeClass('hidden').addClass('next content-hidden');
}

ArticleAnimator.currentElementClone = function () {
    return this.$page.clone().removeClass('hidden').addClass('current');
}

/*
 Helper Functions.
 ************************************************************************/
function elementToTemplate($element) {
    return $element.get(0).outerHTML;
}

function scrollTop() {
    $body.add($html).scrollTop(0);
}

function pageState() {
    return { index: ArticleAnimator.currentPostIndex, current: elementToTemplate(ArticleAnimator.$current), next: elementToTemplate(ArticleAnimator.$next) }
}

function getURLIndex() {
    return parseInt((history.state && history.state.index) || window.location.hash.replace('#', "") || ArticleAnimator.currentPostIndex);
}


function initialTextComment() {
    $('.editable').children().each(function () {
        if ($(this).text().length > 0) {
            $(this).append($('#tooltip-template').html());
            $(this).hover(function () {
                $('.tooltip').hide();
                $(this).find('.tooltip').show();

            });
        }
    });

    $('.tooltip').hover(function () {
        $(this).find('.tooltip-arrow').css('border-top-color', 'rgb(23, 163, 18)');
        $(this).find('.tooltip-inner').css('background', 'rgb(23, 163, 18)');
    }, function () {
        $(this).find('.tooltip-arrow').css('border-top-color', '#000000');
        $(this).find('.tooltip-inner').css('background', '#000000');
    });
}

var commentMenuLeft = document.getElementById('cbp-spmenu-s2');
var closePush = document.getElementById('cbp-comment-close');
function initialCommentSnapper() {
    var showRightPush = document.getElementById('commentmenubtn');
    if (showRightPush != null) {
        showRightPush.onclick = function () {
            classie.toggle(this, 'active');
            classie.toggle(body, 'cbp-spmenu-push-toright');
            classie.toggle(commentMenuLeft, 'cbp-spmenu-open');
        };

        closePush.onclick = function () {
            closeCommentsSnapper();
        };
    }
}

/*
 Document ready.
 ************************************************************************/
$(document).ready(function () {

    /* A couple of selections. */
    $body = $(document.body);
    $window = $(window);
    $html = $(document.documentElement);

    /* Let's get it started. */
    ArticleAnimator.load();
    $('.mediumInsert').remove();
    initialCommentSnapper();

});


function showCommentForm() {
    $('.notes-edit-mode').show();
    $('.notes-add').hide();
}

function cancelAddCommand() {
    $('.notes-edit-mode').hide();
    $('.notes-add').show();
}
function saveComment(guid) {
    var usercomment = $('.notes-edit-mode .editable p').html();
    $.ajax({
        url: '/register/addcomment',
        type: 'post',
        data: {guid: guid, usercomment: usercomment},
        success: function (result) {
            if (result) {
                cancelAddCommand();
                closeCommentsSnapper();
                var currentamount = parseInt($('.detailpage.current .commentamount').html());
                $('.detailpage.current .commentamount').html(currentamount + 1);
            }
        }
    });
}

var commentsShowed = false;
var commentContentTemplate = '<div class="notes-note">' +
    '<div class="notes-note-inner">' +
    '<div class="notes-note-main"><span class="notes-state-border"></span>' +
    '<a href="/profile?guid={userguid}" class="notes-avatar">' +
    '<img src="{userimage}" class="notes-author-icon">' +
    '</a>' +
    '<div class="notes-author">' +
    '<a href="/profile?guid={userguid-1}" title="Go to the profile of Dikke Miserie">{user}</a>' +
    '</div>' +
    '<div class="notes-content" style="color: #fff;">{content}</div>' +
    '<div class="notes-foot no-user-select"></div></div></div></div>';
function showComments(guid, page) {

    $('#commentmenubtn').click();
    if (!commentsShowed) {
        $.ajax({
            url: '/musiccomments',
            type: 'post',
            data: {guid: guid, page: page},
            success: function (result) {
                $('.notes-list .notes-items').empty();
                $.each(result, function (i, e) {
                    $('.notes-list .notes-items').append(commentContentTemplate
                        .replace('{userguid}', e.user.guid)
                        .replace('{userguid-1}', e.user.guid)
                        .replace('{userimage}', e.user.weiboImage)
                        .replace('{user}', e.user.weiboDisplayName)
                        .replace('{content}', e.comment));
                });
                $('.submitcommentbtn').attr('onclick', 'saveComment("' + guid + '");');


            }
        });
    }
    commentsShowed = !commentsShowed;
}

function closeCommentsSnapper() {
    $('#commentmenubtn').click();
    $('.notes-edit-mode .editable p').html('输入评论');
    commentsShowed = false;
}