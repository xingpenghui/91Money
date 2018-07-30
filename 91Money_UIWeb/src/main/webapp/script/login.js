$(document).ready(function() {
    $('#loginForm').validate({
        debug:true,
        rules:{
            username: {
                required: true
            },
            password: {
                required: true
            }
        },
        messages:{
            username: {
                required: '请输入用户名'
            },
            password: {
                required: '请输入密码'
            }
        },
        submitHandler:function(form){
            $(form).ajaxSubmit({
                method:$("#fm1").attr("method"),
                xhrFields: {withCredentials: true},
                data:$("#fm1").serialize(),
                success:function (obj) {
                    if(obj.code==0){
                        location.href="index.html";
                    }else{
                        alert("登录失败");
                    }
                }
            });
        }
    });
});


