function verify(guid,obj){
    $.ajax({
        url: '/admin/allowpublish',
        type: 'post',
        data: {guid:guid},
        success: function (result) {
            if(result){
                $(obj).html('<span class="fui-cross"></span>');
                $(obj).addClass('active');
            }
        }
    });
}
function reject(guid,obj){
    $.ajax({
        url: '/admin/reject',
        type: 'post',
        data: {guid:guid},
        success: function (result) {
            if(result){
                $(obj).html('<span class="fui-cross"></span>');
                $(obj).addClass('active');
            }
        }
    });
}