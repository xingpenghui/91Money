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
                type: 'post',
                dataType: 'json',
                success: function(r){
                    console.log(r);
                    if(r.code == 0){
                        window.location.href = 'index';
                    }else{
                        changeYzm();
                        layer.alert(r.msg);
                    }
                }
            });
        }
    });

});


function changeYzm(){
    console.log('change==================');
    $('#yanzheng').attr('src', 'captcha.jpg?' + new Date());
}


