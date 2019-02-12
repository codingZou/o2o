$(function () {
    var shopId = getQueryString('shopId');
    var isEdit = shopId ? true : false;
    var initUrl = '/o2o/shopadmin/getshopinitinfo';
    var registerShopUrl = '/o2o/shopadmin/registershop';
    var shopInfoUrl = "/o2o/shopadmin/getshopbyid?shopId=" + shopId;
    var editShopUrl = "/o2o/shopadmin/updateshop";
    if (isEdit) {
        getEditShopInfo(shopId);
    } else {
        getShopInitInfo();
    }

    function getEditShopInfo(shopId) {
        $.getJSON(shopInfoUrl, function (data) {
            if (data.success) {
                var shop = data.shop;
                $("#shop-name").val(shop.shopName);
                $("#shop-addr").val(shop.shopAddr);
                $("#shop-phone").val(shop.phone);
                $("#shop-desc").val(shop.shopDesc);
                /*$.each(shop.shopCategory, function (index, item) {
                    $("#shop-category").append('<option value="'
                        + item.shopCategoryId + '">' + item.shopCategoryName + '</option>');
                })*/
                $("#shop-category").append('<option value="'
                    + shop.shopCategory.shopCategoryId + '">' + shop.shopCategory.shopCategoryName + '</option>');
                // $("shop-category option").val(shop.shopCategory.shopCategoryName);
                $("#shop-category").attr('disabled', 'disabled');
                $.each(data.areaList, function (index, item) {
                    $("#area").append('<option value="'
                        + item.areaId + '">' + item.areaName + '</option>');
                })
            }
        })
    }

    function getShopInitInfo() {
        $.getJSON(initUrl, function (data) {
            if (data.success) {
                $.each(data.shopCategoryList, function (index, item) {
                    $("#shop-category").append('<option value="'
                        + item.shopCategoryId + '">' + item.shopCategoryName + '</option>');
                })
                $.each(data.shopAreaList, function (index, item) {
                    $("#area").append('<option value="'
                        + item.areaId + '">' + item.areaName + '</option>');
                })
            }
        })
    }

    $("#submit").click(function () {
        var shop = {};
        shop.shopId = shopId;
        shop.shopName = $("#shop-name").val();
        shop.shopAddr = $("#shop-addr").val();
        shop.phone = $("#shop-phone").val();
        shop.shopDesc = $("#shop-desc").val();
        /*shop.shopCategory = {
            shopCategoryId: $("#shop-category").find("option").not(function () {
                return !this.selected;
            }).data("id")
        };*/
        /*shop.area = {
            areaId: $("#area").find("option").not(function () {
                return !this.selected;
            }).data("id")
        };*/
        shop.shopCategory = {
            shopCategoryId: $("#shop-category option:selected").val()
        };
        shop.area = {
            areaId: $("#area option:selected").val()
        };
        var shopImg = $("#shop-img")[0].files[0];
        var formData = new FormData();
        if (!shopImg) {
            alert("请输入上传的图片");
            return;
        }
        formData.append("shopImg", shopImg);
        formData.append("shopStr", JSON.stringify(shop));
        var verifyCodeActual = $("#j-captcha").val();
        if (!verifyCodeActual) {
            alert("请输入验证码！");
            return;
        }
        formData.append("verifyCodeActual", verifyCodeActual);
        $.ajax({
            url: (isEdit ? editShopUrl : registerShopUrl),
            type: "POST",
            data: formData,
            contentType: false,
            processData: false,
            cache: false,
            success: function (data) {
                if (data.success) {
                    alert("提交成功");
                } else {
                    alert("提交失败" + "原因为：" + data.errMsg);
                    $("#captcha_img").click();
                }
            }
        });
    })
})