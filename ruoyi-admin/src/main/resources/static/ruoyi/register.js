$(function() {
    validateRule();
    // 获取邮箱验证码
    $('#btnSendCode').click(function() {
        var email = $.common.trim($("input[name='email']").val());
        if ($.common.isEmpty(email)) {
            $.modal.msg("请输入邮箱");
            return false;
        }
        if (!/^[\w.\-]+@[\w\-]+(\.[\w\-]+)+$/.test(email)) {
            $.modal.msg("邮箱格式不正确");
            return false;
        }
        var $btn = $(this);
        $.ajax({
            type: "post",
            url: ctx + "register/sendEmailCode",
            data: { "email": email },
            beforeSend: function () {
                $btn.prop('disabled', true);
                $btn.html('<i class="fa fa-spinner fa-spin"></i> 发送中...');
            },
            success: function(r) {
                if (r.code == web_status.SUCCESS) {
                    $.modal.msg("验证码已发送，请查收邮箱");
                    // 60秒倒计时
                    var seconds = 60;
                    var timer = setInterval(function() {
                        seconds--;
                        if (seconds <= 0) {
                            clearInterval(timer);
                            $btn.prop('disabled', false).text("重新获取");
                        } else {
                            $btn.text(seconds + "s 后重试");
                        }
                    }, 1000);
                } else {
                    $btn.prop('disabled', false).text("获取验证码");
                    $.modal.msg(r.msg);
                }
            },
            error: function() {
                $btn.prop('disabled', false).text("获取验证码");
                $.modal.msg("发送失败，请稍后重试");
            }
        });
    });
});

function register() {
    var username = $.common.trim($("input[name='username']").val());
    var email = $.common.trim($("input[name='email']").val());
    var emailCode = $.common.trim($("input[name='emailCode']").val());
    var password = $.common.trim($("input[name='password']").val());
    $.ajax({
        type: "post",
        url: ctx + "register",
        data: {
            "loginName": username,
            "email": email,
            "emailCode": emailCode,
            "password": password
        },
        beforeSend: function () {
            $.modal.loading($("#btnSubmit").data("loading"));
        },
        success: function(r) {
            if (r.code == web_status.SUCCESS) {
            	layer.alert("<font color='red'>恭喜你，您的账号 " + username + " 注册成功！</font>", {
            	    icon: 1,
            	    title: "系统提示"
            	},
            	function(index) {
            	    //关闭弹窗
            	    layer.close(index);
            	    location.href = ctx + 'login';
            	});
            } else {
            	$.modal.closeLoading();
            	$.modal.msg(r.msg);
            }
        }
    });
}

function validateRule() {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#registerForm").validate({
        rules: {
            username: {
                required: true,
                minlength: 2
            },
            email: {
                required: true,
                email: true
            },
            emailCode: {
                required: true,
                minlength: 4
            },
            password: {
                required: true,
                minlength: 5,
                specialSign: true
            },
            confirmPassword: {
                required: true,
                equalTo: "[name='password']"
            }
        },
        messages: {
            username: {
                required: icon + "请输入您的用户名",
                minlength: icon + "用户名不能小于2个字符"
            },
            email: {
                required: icon + "请输入您的邮箱",
                email: icon + "邮箱格式不正确"
            },
            emailCode: {
                required: icon + "请输入邮箱验证码",
                minlength: icon + "验证码长度不正确"
            },
            password: {
            	required: icon + "请输入您的口令",
                minlength: icon + "口令不能小于5个字符",
            },
            confirmPassword: {
                required: icon + "请再次输入您的口令",
                equalTo: icon + "两次口令输入不一致"
            }
        },
        submitHandler: function(form) {
            register();
        }
    })
}
