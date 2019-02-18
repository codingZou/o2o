$(function () {
    // 获取该店铺下的商品列表的url
    var productListUrl = "/o2o/shopadmin/getproductlistbyshop?pageIndex=1&pageSize=999";
    // 商品下架Url
    var statusUrl = "/o2o/shopadmin/updateproduct";
    getProductList();

    function getProductList() {
        $.getJSON(productListUrl, function (data) {
            if (data.success) {
                var productList = data.productList;
                // 遍历每条商品信息,拼接成一行显示，列信息包括:
                // 商品名称,优先级,上架\下架按钮(含productId),编辑按钮(含productId)
                // 预览(含productId)
                $.each(productList, function (index, item) {
                    var textOp = "下架";
                    var contraryStatus = 0;
                    if (item.enableStatus == 0) {
                        // 若状态值为0,表明是已下架商品,操作变为上架(即点击上架按钮上架相关商品)
                        textOp = "上架";
                        contraryStatus = 1;
                    } else {
                        contraryStatus = 0;
                    }
                    // 拼接每件商品的行信息
                    $(".product-wrap").append("<div class='row row-product'>"
                        + "<div class='col-33'>" + item.productName + "</div>"
                        + "<div class='col-20'>" + item.priority + "</div>"
                        + "<div class='col-40'>" + "<a href='#' class='edit' data-id='"
                        + item.productId + "' data-status='" + item.enableStatus + "'>编辑</a>"
                        + "<a href='#' class='status' data-id='" + item.productId + "'data-status='"
                        + contraryStatus + "'>" + textOp + "</a>" + "<a href='#' class='preview' data-id='"
                        + item.productId + "'>预览</a>" + "</div></div>")
                })
            }
        })
    }

    $(".product-wrap").on("click", "a", function (e) {
        var target = $(e.currentTarget);
        if (target.hasClass("edit")) {
            // 进入店铺信息编辑页面,并带有productId参数
            window.location.href = "/o2o/shopadmin/productoperation?productId="
                + e.currentTarget.dataset.id;
        } else if (target.hasClass("status")) {
            changeItemStatus(e.currentTarget.dataset.id,
                e.currentTarget.dataset.status);
        } else if (target.hasClass("preview")) {
            window.location.href = "/o2o/frontend/productdetail?productId="
                + e.currentTarget.dataset.id;
        }
    });

    function changeItemStatus(id, enableStatus) {
        // 定义product json对象并添加productId以及状态(上架/下架)
        var product = {};
        product.productId = id;
        product.enableStatus = enableStatus;
        $.confirm("确认吗?", function () {
            $.ajax({
                url: statusUrl,
                type: "post",
                data: {
                    productStr: JSON.stringify(product),
                    statusChange: true
                },
                dataType: "json",
                success: function (data) {
                    if (data.success) {
                        $.toast("操作成功!");
                        $(".product-wrap").empty();
                        getProductList();
                    } else {
                        $.toast("操作失败!" + data.errMsg)
                    }
                }
            })
        })

    }

    $("#new").click(function () {
        window.location.href = "/o2o/shopadmin/productoperation";
    });
})
;