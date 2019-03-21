$(function () {
    var loading = false;
    // 分页返回的最大条数,超过则禁止访问后台
    var maxItems = 20;
    // 一页返回的商品数
    var pageSize = 1;
    // 商品列表的url
    var listUrl = "/o2o/frontend/listproductbyshop";
    // 默认页码
    var pageNum = 1;
    // 从地址栏里取出shopId
    var shopId = getQueryString("shopId");
    var productCategoryId = "";
    var productName = "";
    //店铺信息以及商品类别列表的url
    var searchDivUrl = "/o2o/frontend/listshopdetailpageinfo?shopId="
        + shopId;

    // 渲染出店铺基本信息以及商品类别列表以供搜索
    getSearchDivData();
    // 预先加载pageSize条商品信息
    addItems(pageSize, pageNum);

    /**
     * 渲染出店铺基本信息以及商品类别列表以供搜索
     */
    function getSearchDivData() {
        var url = searchDivUrl;
        $.getJSON(url, function (data) {
            if (data.success) {
                var shop = data.shop;
                $("#shop-cover-pic").attr("src", shop.shopImg);
                $("#shop-update-time").html(
                    new Date(shop.lastEditTime)
                        .Format("yyyy-MM-dd"));
                $("#shop-name").html(shop.shopName);
                $("#shop-desc").html(shop.shopDesc);
                $("#shop-addr").html(shop.shopAddr);
                $("#shop-phone").html(shop.phone);
                //该商铺的商品类别列表
                var productCategoryList = data.productCategoryList;
                var html = "";
                $.each(productCategoryList, function (index, item) {
                    html += "<a href='#' class='button' data-product-search-id="
                        + item.productCategoryId
                        + ">"
                        + item.productCategoryName
                        + "</a>";
                });
                $("#shopdetail-button-div").html(html);
            }
        });
    }

    /**
     * 获取分页展示的商品列表信息
     * @param pageSize
     * @param pageIndex
     */
    function addItems(pageSize, pageIndex) {
        // 拼接出查询的url,赋空值默认去掉这个条件,有值代表按照这个条件拼接
        var url = listUrl + "?" + "pageIndex=" + pageIndex + "&pageSize="
            + pageSize + "&productCategory.productCategoryId=" + productCategoryId
            + "&productName=" + productName + "&shop.shopId=" + shopId;
        loading = true;
        $.getJSON(url, function (data) {
            if (data.success) {
                maxItems = data.count;
                var html = "";
                $.each(data.productList, function (index, item) {
                    html += "" + "<div class='card' data-product-id="
                        + item.productId + ">"
                        + "<div class='card-header'>" + item.productName
                        + "</div>" + "<div class='card-content'>"
                        + "<div class='list-block media-list'>" + "<ul>"
                        + "<li class='item-content'>" + "<div class='item-media'>"
                        + "<img src='" + item.imgAddr + "'width = '44'> " + " </div>"
                        + "<div class='item-inner'>" + "<div class='item-subtitle'>" + item.productDesc
                        + "</div>" + "</div>" + "</li>" + "</ul>"
                        + "</div>" + "</div>" + "<div class='card-footer'>"
                        + "<p class='color-gray'>" + new Date(item.lastEditTime).Format("yyyy-MM-dd")
                        + "更新</p>" + "<span>点击查看</span>" + "</div>"
                        + "</div>";
                });
                $(".list-div").append(html);
                var total = $(".list-div .card").length;
                var flag = false;
                if (total >= maxItems) {
                    // 隐藏提示符
                    $(".infinite-scroll-preloader").hide();
                    flag = true;
                } else {
                    // 显示提示符
                    $(".infinite-scroll-preloader").show();
                }
                pageNum += 1;
                if (flag) {
                    // 后台不需要再加载
                    loading = true;
                } else {
                    // 加载结束可以再次加载
                    loading = false;
                }
                // 刷新页面，显示新加载的店铺
                $.refreshScroller();
            }
        });
    }

    // 下滑屏幕自动进行分页搜索
    $(document).on("infinite", ".infinite-scroll-bottom", function () {
        if (loading)
            return;
        addItems(pageSize, pageNum);
    });

    // 选择新的商品类别之后，重置页码,清空原来的商品列表,按照新的类别去查询
    $("#shopdetail-button-div").on("click", ".button",
        function (e) {
            // 获取商品类别Id
            productCategoryId = e.target.dataset.productSearchId;
            if (productCategoryId) {
                // 若之前已经选定了别的category,则移除其选定的效果,改成选定新的
                if ($(e.target).hasClass("button-fill")) {
                    $(e.target).removeClass("button-fill");
                    productCategoryId = "";
                } else {
                    $(e.target).addClass("button-fill").siblings()
                        .removeClass("button-fill");
                }
                $(".list-div").empty();
                pageNum = 1;
                addItems(pageSize, pageNum);
            }
        });
    // 点击商品的卡片进入该商品的详情页
    $(".list-div").on("click", ".card", function (e) {
        var productId = e.currentTarget.dataset.productId;
        window.location.href = "/o2o/frontend/productdetail?productId=" + productId;
    });
    // 需要查询的商品名字发生变化后,重置页码,清空原来的商品列表，按照新的名字去查询
    $("#search").on("change", function (e) {
        productName = e.target.value;
        $(".list-div").empty();
        pageNum = 1;
        addItems(pageSize, pageNum);
    });
    // 点击后打开右侧栏
    $("#me").click(function () {
        $.openPanel("#panel-right-demo");
    });
    // 初始化页面
    $.init();
});