$(function () {
    var loading = false;
    // 分页允许返回的最大条数，超过此数则禁止访问后台
    var maxItems = 999;
    // 一页返回的最大条数
    var pageSize = 4;
    // 获取店铺列表的url
    var listUrl = "/o2o/frontend/listshop";
    // 获取店铺类别列表以及区域列表的url
    var searchDivUrl = "/o2o/frontend/listshoppageinfo";
    // 页码
    var pageNum = 1;
    // 从地址栏url里获取parentId
    var parentId = getQueryString("parentId");
    var areaId = "";
    var shopCategoryId = "";
    var shopName = "";
    // 渲染店铺列表以及区域列表以供搜索
    getSearchDivData();
    // 预加载十条店铺信息
    addItems(pageSize, pageNum);

    function getSearchDivData() {
        // 如果传入了parentId,则取出一级类别下的所有二级类别(如果为空传null也没问题)
        var url = searchDivUrl + "?" + "parentId=" + parentId;
        $.getJSON(url, function (data) {
            if (data.success) {
                // 获取后台返回的店铺类别列表
                var html = "";
                var shopCategoryList = data.shopCategoryList;
                // 遍历店铺类别列表,拼接出a标签集
                html += "<a href='#' class='button' data-category-id=''>全部类别</a>";
                $.each(shopCategoryList, function (index, item) {
                    html += "<a href='#' class='button' data-category-id='"
                        + item.shopCategoryId + "'>" + item.shopCategoryName + "</a>";
                });
                $("#shoplist-search-div").html(html);
                var selectOptions = "<option value=''>全部区域</option>";
                // 获取区域列表
                var areaList = data.areaList;
                $.each(areaList, function (index, item) {
                    selectOptions += "<option value='" + item.areaId
                        + "'>" + item.areaName + "</option>"
                });
                // 将标签集添加进area列表
                $("#area-search").html(selectOptions);
            }
        })
    }

    function addItems(pageSize, pageIndex) {
        // 拼接出查询的url,赋空值默认去掉这个条件,有值代表按照这个条件拼接
        var url = listUrl + '?' + "pageIndex=" + pageIndex + "&pageSize=" + pageSize
            + "&parentId=" + parentId + "&area.areaId=" + areaId
            + "&shopCategory.shopCategoryId=" + shopCategoryId + "&shopName=" + shopName;
        //设定加载符,若还在后台取数据则不能再次访问后台,避免重复加载
        loading = true;
        // 条件查询店铺列表
        $.getJSON(url, function (data) {
            if (data.success) {
                // 获取当前条件下的店铺总数
                maxItems = data.count;
                var html = "";
                // 遍历店铺列表，拼接出卡片集合
                $.each(data.shopList, function (index, item) {
                    html += "" + "<div class='card' data-shop-id='" + item.shopId + "'>"
                        + "<div class='card-header'>" + item.shopName + "</div>"
                        + "<div class='card-content'>" + "<div class='list-block media-list'>"
                        + "<ul>" + "<li class='item-content'>" + "<div class='item-media'>"
                        + "<img src='" + item.shopImg + "'width='44'>" + "</div>"
                        + "<div class='item-inner'>" + "<div class='item-subtitle'>" + item.shopDesc
                        + "</div>" + "</div>" + "</li>" + "</ul>"
                        + "</div>" + "</div>" + "<div class='card-footer'>" + "<p class='color-gray'>"
                        + new Date(item.lastEditTime).Format("yyyy-MM-dd") + "更新</p>"
                        + "<span>点击查看</span>" + "</div>" + "</div>";
                });
                $(".list-div").append(html);
                // 获取目前为止已经显示的卡片总数,包含之前已经加载的
                var total = $(".list-div .card").length;
                var flag = false;
                // 若总数达到查询出来的总数，则停止后台的加载
                if (total >= maxItems) {
                    // 隐藏提示符
                    $(".infinite-scroll-preloader").hide();
                    flag = true;
                } else {
                    // // 显示提示符
                    $(".infinite-scroll-preloader").show();
                }
                if (flag) {
                    // 后台不需要再加载
                    loading = true;
                } else {
                    // 加载结束,可以再次加载
                    loading = false;
                }
                // 否则页面加一,继续加载出新的店铺
                pageNum += 1;
                // 刷新页面, 显示新加载的店铺
                $.refreshScroller();
            }
        })
    }

    // 下滑屏幕自动进行分页搜索
    $(document).on("infinite", ".infinite-scroll-bottom", function () {
        if (loading)
            return;
        addItems(pageSize, pageNum);
    });
    // 点击店铺的卡片进入该店铺的详情页
    $(".shop-list").on("click", ".card", function (e) {
        var shopId = e.currentTarget.dataset.shopId;
        window.location.href = "/o2o/frontend/shopdetail?shopId=" + shopId;
    })
    // 选择新的店铺类别之后，重置页码,清空原来的店铺列表,按照新的类别去查询
    $("#shoplist-search-div").on("click", ".button", function (e) {
        // 如果传递过来的是一个父类下的子类
        if (parentId) {
            shopCategoryId = e.target.dataset.categoryId;
            //若之前已经选定了别的category,则移除其选定效果,改成选定新的
            if ($(e.target).hasClass("button-fill")) {
                $(e.target).removeClass("button-fill");
                shopCategoryId = "";
            } else {
                $(e.target).addClass("button-fill").siblings().removeClass("button-fill");
            }
            // 由于查询条件改变,清空店铺列表再进行查询
            $(".list-div").empty();
            // 重置页码
            pageNum = 1;
            addItems(pageSize, pageNum);
        } else {
            parentId = e.target.dataset.categoryId;
            if ($(e.target).hasClass("button-fill")) {
                $(e.target).removeClass("button-fill");
                parentId = "";
            } else {
                $(e.target).addClass("button-fill").siblings().removeClass("button-fill");
            }
            // 由于查询条件改变,清空店铺列表再进行查询
            $(".list-div").empty();
            // 重置页码
            pageNum = 1;
            addItems(pageSize, pageNum);
            parentId = "";
        }
    });
    // 需要查询的店铺名字发生变化后,重置页码,清空原先的店铺列表,按照新的名字查询
    $("#search").on("change", function (e) {
        shopName = e.target.value;
        $(".list-div").empty();
        pageNum = 1;
        addItems(pageSize, pageNum)
    });

    // 区域信息发生变化后,重置页码,清空原先的店铺列表,按照新的区域查询
    $("#area-search").on("change", function () {
        areaId = $("#area-search").val();
        $(".list-div").empty();
        pageNum = 1;
        addItems(pageSize, pageNum);
    });

    //点击后打开右侧栏
    /*$("#me").click(function () {
        $.openPanel("#panel-right-demo");
    });*/

    //初始化页面
    $.init();
});