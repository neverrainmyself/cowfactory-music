$(document).ready(function () {
    var editor = new MediumEditor('.editable');
    var headerImageGuid = $('#headerimageguid').val();
    if (headerImageGuid != '') {
        $('#headerimageupload').hide();
        $('.big-header-image').css({ backgroundImage: "url(/loadimage?guid=" + headerImageGuid + ")" });
        $('.big-header-image').css({ height: "420px" });
    }

    $('.editable').mediumInsert({
        editor: editor,
        images: true
    });

    new Medium({
        element: document.getElementById('product-header'),
        mode: 'inline',
        maxLength: 25,
        placeholder: '作品名称'
    });
    new Medium({
        element: document.getElementById('product-sub-header'),
        mode: 'inline',
        maxLength: 25,
        placeholder: '一句话的概括'
    });

    $('.delete-publish-btn').on('click', function () {
        saveBlog('/register/todelete');
        window.location.href = '/intercept?p=1&paras=' + $('#guid').val();
    });

    $('.save-publish-btn').on('click', function () {
        publish();
    });
    $('.save-draft-btn').on('click', function () {
        saveBlog('/register/save');
    });


    initialUpload();

});


function saveBlog(url) {
    var header = $('#product-header p:last').html();
    var content = $('#product-content').html();
    var subTitle = $('#product-sub-header p:last').html();
    $.ajax({
        url: url,
        type: 'post',
        data: {guid: $('#guid').val(), header: header, subheader: subTitle, content: content},
        success: function (result) {
            var p = 2;
            var guid = $('#guid').val();
            if (!result.successded) {
                p = 4;
            }
            window.location.href = '/intercept?p=' + p + '&paras=' + guid + '&paras=' + result.errorMessage;

        }
    });
}
function publish() {
    var header = $('#product-header p:last').html();
    var content = $('#product-content').html();
    var subTitle = $('#product-sub-header p:last').html();
    $.ajax({
        url: '/register/publish',
        type: 'post',
        data: {guid: $('#guid').val(), header: header, subheader: subTitle, content: content},
        success: function (result) {
            var p = 7;
            var guid = $('#guid').val();
            if (!result.successded) {
                p = 6;
            }
            window.location.href = '/intercept?p=' + p + '&paras=' + guid + '&paras=' + result.errorMessage;

        }
    });
}


function initialUpload() {
    $(".plupload.music").each(function () {
        var e = $(this);
        e.pluploadQueue({runtimes: "html5,gears,flash,silverlight,browserplus", url: "/register/uploadmusic?guid=" + $('#guid').val(), max_file_size: "10mb", chunk_size: "10mb", unique_names: !0, resize: {width: 320, height: 240, quality: 90}, filters: [
            {title: "Image files", extensions: "mp3"}
        ], flash_swf_url: "/js/fileupload/plupload.flash.swf", silverlight_xap_url: "/js/fileupload/plupload.silverlight.xap"});
        $(".plupload_header").remove();
        var t = e.pluploadQueue();
        if (e.hasClass("pl-sidebar")) {
            $(".plupload_filelist_header,.plupload_progress_bar,.plupload_start").remove();
            $(".plupload_droptext").html("<span>拖动音乐文件到此</span>");
            $(".plupload_progress").remove();
            $(".plupload_add").text("或者点这里上传");
            t.bind("FilesAdded", function (e, t) {
                setTimeout(function () {
                    e.start()
                }, 500)
            });
            t.bind("FileUploaded", function (e, t) {
                setTimeout(function () {
                    e.splice();
                    $('.plupload').hide();
                    $('.reupload-publish-btn').show();
                    $('.music-play-btns').show();
                }, 500)

            });
            t.bind("QueueChanged", function (e) {
                $('.reupload-publish-btn').hide();
                $(".plupload_droptext").html("<span>拖动音乐文件到此</span>")
            });
            t.bind("StateChanged", function (e) {
                $(".plupload_upload_status").remove();
                $(".plupload_buttons").show()
            })
        } else {
            $(".plupload_progress_container").addClass("progress").addClass("progress-striped");
            $(".plupload_progress_bar").addClass("bar");
            $(".plupload_button").each(function () {
                $(this).hasClass("plupload_add") ? $(this).attr("class", "btn pl_add btn-primary").html("<i class='icon-plus-sign'></i> " + $(this).html()) : $(this).attr("class", "btn pl_start btn-success").html("<i class='icon-cloud-upload'></i> " + $(this).html())
            })
        }
    });

    $('#headerimageupload').click(function () {
        $('#headerimageuploadbtn').click();
    });
    $('#headerimageuploadbtn').change(function () {
        var formData = new FormData();
        formData.append('file', this.files[0]);
        $.ajax({
            url: '/register/uploadheaderimage?guid=' + $('#guid').val(),  //Server script to process data
            type: 'POST',
//            beforeSend: beforeSendHandler,
            success: headerImageUploaded,
//            error: errorHandler,
            // Form data
            data: formData,
            //Options to tell jQuery not to process data or worry about content-type.
            cache: false,
            contentType: false,
            processData: false
        });
    });


}

function headerImageUploaded(response) {
    $('.big-header-image').css({ backgroundImage: "url(/loadimage?guid=" + response + ")" });
    $('.big-header-image').css({ height: "420px" });
    $('#headerimageupload').hide();
    $('.reupload-headerimage').show();
}

function deleteMusicFile() {
    $.ajax({
        url: '/register/removemusicfile',
        type: 'post',
        data: {guid: $('#guid').val()},
        success: function (result) {
            if (result) {
                $('.plupload').show();
                $('.playlist').hide();
                $('.reupload-publish-btn').hide();
                $('.music-play-btns').hide();
                stopPlay(null);
            }

        }
    });
}




