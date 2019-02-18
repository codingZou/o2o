$(function () {
    // 从url里获取productId参数的值
    var productId = getQueryString("productId");
    // 根据productId获取商品信息的url
    var InfoUrl = "/o2o/shopadmin/getproductbyid?productId=" + productId;
    // 获取当前店铺设定的商品类别列表的url
    var categoryUrl = "/o2o/shopadmin/getproductcategorylist";
    // 编辑商品信息的url
    var editProductPostUrl = "/o2o/shopadmin/updateproduct";
    // 添加商品的url
    var addProductUrl = "/o2o/shopadmin/addproduct";
    // 商品添加与编辑是同一页面
    var isEdit = productId ? true : false;
    // 用来标识本次是添加还是编辑操作
    if (isEdit) {
        // 则为编辑操作
        getInfo(productId);
    } else {
        // 添加操作
        // 先获取商品类别列表
        getCategory();
    }

    function getInfo(productId) {
        $.getJSON(InfoUrl, function (data) {
            if (data.success) {
                var product = data.product;
                $("#product-name").val(product.productName);
                $("#product-desc").val(product.productDesc);
                $("#priority").val(product.priority);
                $("#normal-price").val(product.normalPrice);
                $("#promotion-price").val(product.promotionPrice);
                var productCategoryList = data.productCategoryList;
                var optionSelected = product.productCategory.productCategoryId;
                $.each(productCategoryList, function (index, item) {
                    var isSelect = optionSelected === item.productCategoryId ? "selected" : "";
                    $("#category").append("<option data-id='"
                        + item.productCategoryId + "'" + isSelect + ">"
                        + item.productCategoryName + "</option>")

                })
            }

        })
    }

    // 商品添加操作提供该店铺下的所有商品类别列表
    function getCategory() {
        $.getJSON(categoryUrl, function (data) {
            if (data.success) {
                var productCategoryList = data.productCategoryList;
                $.each(productCategoryList, function (index, item) {
                    $("#category").append("<option data-id='"
                        + item.productCategoryId + "'>"
                        + item.productCategoryName + "</option>")
                })
            }
        })
    }

    // 针对商品详情图控件组,若该控件组的最后一个元素发生变化(即上传了图片)
    // 且控件总数未达到6个,则生成新的一个文件上传控件
    $(".detail-img-div").on("change", ".detail-img:last-child", function () {
        if ($(".detail-img").length < 6) {
            $("#detail-img").append("<input type='file' class='detail-img'>");
        }
    });
    $("#submit").click(function () {
            var product = {};
            product.productName = $("#product-name").val();
            product.productDesc = $("#product-desc").val();
            product.priority = $("#priority").val();
            product.normalPrice = $("#normal-price").val();
            product.promotionPrice = $("#promotion-price").val();
            // 获取选定的商品类别值
            product.productCategory = {
                productCategoryId: $('#category').find('option').not(
                    function () {
                        return !this.selected;
                    }).data("id")
                // (此方法需要引入jq库)productCategoryId: $("#category option:selected").val()
            };
            product.productId = productId;
            // 获取缩略图文件流
            var thumbnail = $("#small-img")[0].files[0];
            // 生成表单对象，用于接收参数并传递给后台
            var formData = new FormData();
            formData.append("thumbnail", thumbnail);
            // 遍历商品详情图控件,获取里面的文件流
            $.each($(".detail-img"), function (index, item) {
                // 判断该控件是否已经选择了文件
                if ($(".detail-img")[index].files.length > 0) {
                    // 将第i个文件流赋值给key为productImgi的表单键值对里
                    formData.append("productImg" + index, $(".detail-img")[index].files[0]);
                }
            });
            // 将product json对象转成字符流保存至表单对象key为productStr的键值对里
            formData.append("productStr", JSON.stringify(product));
            //获取表单里输入的验证码
            var verifyCodeActual = $("#j-captcha").val();
            if (!verifyCodeActual) {
                $.toast("请输入验证码！");
                return;
            }
            formData.append("verifyCodeActual", verifyCodeActual);
            // 将数据提交到后台
            $.ajax({
                url: isEdit ? editProductPostUrl : addProductUrl,
                type: "post",
                data: formData,
                contentType: false,
                processData: false,
                cache: false,
                success: function (data) {
                    if (data.success) {
                        $.toast("提交成功!");
                        $("#captcha_img").click();
                    } else {
                        $.toast("提交失败!" + data.errMsg);
                        $("#captcha_img").click();
                    }
                }
            })
        }
    )
});