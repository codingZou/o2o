Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, // 月份
        "d+": this.getDate(), // 日
        "h+": this.getHours(), // 小时
        "m+": this.getMinutes(), // 分
        "s+": this.getSeconds(), // 秒
        "q+": Math.floor((this.getMonth() + 3) / 3), // 季度
        "S": this.getMilliseconds()
        // 毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
            .substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
                : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};

function changeVerifyCode(img) {
    img.src = "../Kaptcha?" + Math.floor(Math.random() * 100);
}

// 使用正则表达式获取url?后面的参数
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return decodeURIComponent(r[2]);
    }
    return '';
}

// 简易的校验表单
function checkForm(shop) {
    if (!shop.shopName) {
        $.toast("商铺名称不能为空");
        return false;
    }
    if (!shop.shopAddr) {
        $.toast("商铺地址不能为空");
        return false;
    }
    if (!shop.phone) {
        $.toast("商铺电话号码不能为空");
        return false;
    }
    if (!$("#shop-img").val()) {
        $.toast("请上传商铺图片");
        return false;
    }
    if (!shop.shopDesc) {
        $.toast("商铺简介不能为空");
        return false;
    }
    // var verifyCodeActual = ;
    if (!$("#j-captcha").val()) {
        $.toast("请输入验证码！");
        return false;
    }
    return true
}