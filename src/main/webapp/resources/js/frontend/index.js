$(function () {
    // 获取头条列表以及一级列表的url
    var listMainInfoUrl = "/o2o/frontend/listmainpageinfo";
    $.getJSON(listMainInfoUrl, function (data) {
        if (data.success) {
            var headLineList = data.headLineList;

            $.each(headLineList, function (index, item) {
                $(".swiper-wrapper").append("<div class='swiper-slide img-wrap'>"
                    + "<a href='" + item.lineLink + "' external><img class='banner-img' src='"
                    + item.lineImg + "' alt='" + item.lineName + "'></a>" + "</div>")
            });
            // 设定轮播图轮时间为3秒
            $(".swiper-container").swiper({
                autoplay: 3000,
                //用户对轮播图进行操作时,是否自动停止autoplay
                autoplayDisableOnInteraction: true
            });
            // 获取后台传递过来的大类列表
            var shopCategoryList = data.shopCategoryList;
            // 遍历大类列表，拼接出两两(col-50)一行的类别
            $.each(shopCategoryList, function (index, item) {
                $(".row").append("<div class='col-50 shop-classify' data-category='"
                    + item.shopCategoryId+ "'>" + "<div class='word'>"
                    + "<p class='shop-title'>" + item.shopCategoryName + "</p>"
                    + "<p class='shop-desc'>" + item.shopCategoryDesc + "</p>" + "</div>"
                    + "<div class='shop-classify-img-warp'>" + "<img class='shop-img' src='"
                    + item.shopCategoryImg + "'>" + "</div>" + "</div>")
            })
        }
    });
    $("#me").click(function () {
        $.openPanel("#panel-right-demo");
    });

    $(".row").on("click", ".shop-classify", function (e) {
        var shopCategroyId = e.currentTarget.dataset.category;
        var newUrl = "/o2o/frontend/shoplist?parentId=" + shopCategroyId;
        window.location.href = newUrl;
    })
});