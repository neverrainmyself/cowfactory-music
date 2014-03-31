var commentMenuLeft;
var closePush;
$(document).ready(function () {
//    if($('.single-music').length){
//        $('.playlist').show();
//    }
    commentMenuLeft = document.getElementById('cbp-spmenu-s2');
    closePush = document.getElementById('cbp-comment-close');
    initialCommentSnapper();
});


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

var commentsShowed = false;
var commentContentTemplate = '<div class="notes-note note_{commentguid}">' +
    '<div class="notes-note-inner">' +
    '<div class="notes-note-main"><span class="notes-state-border"></span>' +
    '<a href="/profile?guid={userguid}" class="notes-avatar">' +
    '<img src="{userimage}" class="notes-author-icon">' +
    '</a>' +
    '<div class="notes-author">' +
    '<a href="/profile?guid={userguid-1}">{user}</a>' +
    '</div>' +
    '<div class="notes-content" style="color: #fff;">{content}</div>' +
    '<div class="notes-foot no-user-select"><a href="javascript:void(0);" id="cbp-comment-close" onclick="deleteComment(\'{commentguid}\',\'{musicguid}\');">删除</a></div></div></div></div>';
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
                        .replace('{content}', e.comment)
                        .replace('{commentguid}', e.guid)
                        .replace('{commentguid}', e.guid)
                        .replace('{musicguid}', guid));
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

function deleteComment(guid,musicguid){
    $.ajax({
        url: '/register/deletecomment',
        type: 'post',
        data: {guid: guid,musicguid:musicguid},
        success: function (result) {
            $('.note_'+guid).remove();
            var currentAmount = parseInt($('.music_item_'+musicguid+' .comment-icon').html());
            $('.music_item_'+musicguid+' .comment-icon').html(currentAmount-1)
        }
    });
}