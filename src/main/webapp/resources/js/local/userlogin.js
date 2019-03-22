$(function () {
    var loginUrl = "/o2o/local/userlogincheck";
    // 1代表客户2代表商家
    var usertype = getQueryString("usertype");
    var loginCount = 0;
    $("#submit").click(function () {
        var userName = $("#username").val();
        var password = $("#psw").val();
        var verifyCodeActual = $("#j_captcha").val();
        var needVerify = false;
        if (loginCount >= 3) {
            if (!verifyCodeActual) {
                $.toast("请输入验证码！");
                return;
            } else {
                needVerify = true;
            }
        }
        $.ajax({
            url: loginUrl,
            async: false,
            cache: false,
            type: "post",
            dataType: "json",
            data: {
                usertype: usertype,
                userName: userName,
                password: password,
                verifyCodeActual: verifyCodeActual,
                needVerify: needVerify
            },
            success: function (data) {
                if (data.success) {
                    $.toast("登录成功！");
                    if (usertype == 1) {
                        window.location.href = "/o2o/frontend/index";
                    } else {
                        window.location.href = "/o2o/shopadmin/shoplist";
                    }
                } else {
                    $.toast("登录失败！" + data.errMsg);
                    loginCount++;
                    if (loginCount >= 3) {
                        $("#verifyPart").show();
                    }
                    $("#captcha_img").click();
                }
            }
        });
    });

    $("#register").click(function () {
        window.location.href = "/o2o/local/userregister?usertype=" + usertype;
    });
});