$(function () {
    var url = "/o2o/shopadmin/updatelocalauthpwd";
    $("#submit").click(function () {
        /*
        var formData = new FormData();
        formData.append("userName", userName);
        formData.append("password", password);
        formData.append("newPassword", newPassword);


        formData.append("verifyCodeActual", verifyCodeActual);*/
        // var formData = new FormData();
        var userName = $("#userName").val();
        var password = $("#password").val();
        var newPassword = $("#newPassword").val();
        var verifyPassword = $("#verifyPassword").val();
        var verifyCodeActual = $("#j_captcha").val();
        if (newPassword != verifyPassword) {
            $.toast("两次密码不一致！");
            return;
        }
        if (!verifyCodeActual) {
            $.toast("请输入验证码！");
            return;
        }
        $.ajax({
            url: url,
            type: "POST",
            data: {
                userName: userName,
                password: password,
                newPassword: newPassword,
                verifyCodeActual: verifyCodeActual
            },
            /*contentType: false,
            processData: false,
            cache: false,*/
            success: function (data) {
                if (data.success) {
                    $.toast("提交成功！");
                    window.location.href = "/o2o/shopadmin/userlogin";
                } else {
                    $.toast("提交失败！" + data.errMsg);
                    $("#captcha_img").click();
                }
            }
        });
    });

    $("#back").click(function () {
        window.location.href = "/o2o/shopadmin/shoplist";
    });
});
