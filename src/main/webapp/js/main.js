var menuLeft = document.getElementById('cbp-spmenu-s1'),
    body = document.body;

var audio;
var detailLikeTemplate = '<li class="upvoters-item likeduser{userguid}"><a href="{userprofileurl}" class="avatar-chromeless avatar-icon"><img src="{userimageurl}"></a></li>';

$(document).ready(function () {
    initialSnapper();
    var a = audiojs.createAll();
    audio = a[0];
    if(audio !=undefined){
        audio.trackEnded = function () {
            var currentPlayingSong = $('.currentplayingsong');
            if(currentPlayingSong!=null){
                var nextSong = currentPlayingSong.parent().parent().parent().next();
                if(nextSong.length==0){
                    nextSong =  $('.single-music:first');
                }else{
                    nextSong = nextSong.find('.single-music');
                }
                playOneFromList(nextSong.attr('src'), nextSong);
            }else{
                playOneFromList(null, null);
            }
        }
        $(document).keydown(function(e) {
            var unicode = e.charCode ? e.charCode : e.keyCode;
            // right arrow
            if (unicode == 39) {
                var currentPlayingSong = $('.currentplayingsong');

                if(currentPlayingSong!=null){
                    var nextSong = currentPlayingSong.parent().parent().parent().next();

                    if(nextSong.length==0){
                        nextSong =  $('.single-music:first');
                    }else{
                        nextSong = nextSong.find('.single-music');
                    }
                    playOneFromList(nextSong.attr('src'), nextSong);
                }
            }
        })
    }


});

function initialSnapper() {

    var showLeftPush = document.getElementById('showLeftPush');
    if (showLeftPush != null) {

        var closePush = document.getElementById('cbp-close');
        showLeftPush.onclick = function () {
            classie.toggle(this, 'active');
            classie.toggle(body, 'cbp-spmenu-push-toright');
            classie.toggle(menuLeft, 'cbp-spmenu-open');
        };
        closePush.onclick = function () {
            $('#showLeftPush').click();
        };
    }

}


function btnGroupClick(obj) {
    $('.btn-group .single-music').removeClass('active');
    $('.btn-group .stop-music').removeClass('active');
    $(obj).addClass('active');
}

function playOneFromList(guid, obj) {
    if (guid == null) {
        obj = $('.single-music:first');
        guid = obj.attr('src');
    }

    if(obj.length==0){
        $('.playlist .scrubber h3').html('暂时无法收听');
    }else{
        var name = obj.attr('name');
        $('.currentplayingsong').removeClass('currentplayingsong');
        obj.addClass("currentplayingsong")

        var nextSongObj = obj.parent().parent().parent().next();

        if (nextSongObj.length==0) {
            nextSongObj = $('.single-music:first');
        }else{
            nextSongObj = nextSongObj.find('.single-music');
        }


        var nextSongName = nextSongObj.attr("name");
        $('.playlist .scrubber h3').html(name);
        $('.playlist .time h3').html(nextSongName);
        audio.load('/loadmusic?guid=' + guid);
        audio.play();
        btnGroupClick(obj);
        $('.playlist').show();

        listened(guid);
    }

}

function playOne(guid,name){
    audio.load('/loadmusic?guid=' + guid);
    audio.play();
    $('.playlist').show();
    $('.playlist .scrubber h3').html(name);
    listened(guid);
}
function stopPlay(obj) {
    audio.pause();
    if(obj!=null){
        btnGroupClick(obj);
    }
}

function listened(guid){
    $.ajax({
        url: '/listened',
        type: 'get',
        data: {guid:guid},
        success: function (result) {
            console.log("listened "+ guid);
        }
    });
}


function liked(guid,obj){
    $.ajax({
        url: '/register/like',
        type: 'post',
        data: {guid:guid},
        success: function (result) {
            if(result.liked){
                $(obj).addClass('active');
                if($('.detailpage.current .upvoters li').length<3){
                    var likedResult = detailLikeTemplate
                        .replace('{userguid}', result.user.guid)
                        .replace('{userprofileurl}','/profile?guid='+result.user.guid)
                        .replace('{userimageurl}',result.user.weiboImage);
                    if($('.detailpage.current').length>0){
                        var currentLiked = parseInt($('.detailpage.current .fui-heart').html());
                        $('.detailpage.current .fui-heart').html(currentLiked + 1);
                        $('.detailpage.current .upvoters').append(likedResult);
                    }

                }
            }else{
                $(obj).removeClass('active');
                if($('.detailpage.current').length>0){
                    var currentLiked = parseInt($('.detailpage.current .fui-heart').html());
                    $('.detailpage.current .fui-heart').html(currentLiked - 1);
                    $('.detailpage.current .upvoters .likeduser'+result.user.guid).remove();
                }
            }
        }
    });
}




