$(function () {
    $('#login-form').submit(function () {
        $.ajax({
            type:"post",
            url:"/subLogin",
            data:$('#login-form').serialize(),
            success:function (data) {
               if(data.status==200){
                   new $.flavr({ content : '登录中...',
                       autoclose : true,
                       timeout : 500,
                        onClose:function () {
                            window.location.href = "/home";
                       }
                   });//500毫秒

               }else{
                   new $.flavr({ content : data.msg,autoclose : false});
               }
            }
        });
        return false;
    });
});