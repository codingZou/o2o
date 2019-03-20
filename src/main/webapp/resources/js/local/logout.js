$(function () {
    // 登出系统
    $("#log-out").click(function () {
        $.ajax({
            url: "/o2o/local/logout",
            type: "post",
            success: function (data) {
                if (data.success) {
                    var usertype = $("#log-out").attr("usertype");
                    window.location.href = "/o2o/local/userlogin?usertype=" + usertype;
                }
            }
        });

    });
});