$(function () {
    var listUrl = "/o2o/shopadmin/getproductcategorylist";
    var addUrl = "/o2o/shopadmin/addproductcategory";
    var deleteUrl = "/o2o/shopadmin/deleteproductcategory";
    getList();

    function getList() {
        $.getJSON(listUrl, function (data) {
            if (data.success) {
                $.each(data.productCategoryList, function (index, item) {
                    $("#product-category-list").append("<div class='row row-product-category now'>"
                        + "<div class='col-33 product-category-name'>"
                        + item.productCategoryName
                        + "</div><div class='col-33'>"
                        + item.priority + "</div><div class='col-33'>"
                        + "<a href='#' class='button delete' data-id='" + item.productCategoryId + "'>删除</a>"
                        + "</div></div>")
                })
            }
        })
    }

    $("#new").click(function () {
        $("#product-category-list").append("<div class='row row-product-category temp'>"
            + "<div class='col-33'><input class='category-input category' type='text ' placeholder='类别名'></div>"
            + "<div class='col-33'><input class='category-input priority' type='number ' placeholder='优先级'></div>"
            + "<div class='col-33'><a href='#' class='button delete'>删除</a></div>"
            + "</div>")
    });
    $("#submit").click(function () {
        var tempArr = $(".temp");
        var productCategoryList = [];
        $.each(tempArr, function (index, item) {
            var tempObj = {};
            tempObj.productCategoryName = $(item).find(".category").val();
            tempObj.priority = $(item).find(".priority").val();
            if (tempObj.productCategoryName && tempObj.priority) {
                productCategoryList.push(tempObj);
            }
        });
        $.ajax({
            url: addUrl,
            type: "post",
            data: JSON.stringify(productCategoryList),
            contentType: "application/json",
            success: function (data) {
                if (data.success) {
                    $.toast("提交成功!");
                    $("#product-category-list").empty();
                    getList();
                } else {
                    $.toast("提交失败!");
                }
            }
        })
    });
    $("#product-category-list").on('click', '.row-product-category.temp .delete',
        function (e) {
            // console.log($(this).parent().parent());
            $(this).parent().parent().remove();
        })
    $("#product-category-list").on('click', '.row-product-category.now .delete',
        function (e) {
            var target = e.currentTarget;
            $.confirm("确定删除吗?", function () {
                $.ajax({
                    url: deleteUrl,
                    type: "post",
                    data: {
                        productCategoryId: target.dataset.id
                    },
                    dataType: "json",
                    success: function (data) {
                        if (data.success) {
                            $.toast("删除成功!");
                            $("#product-category-list").empty();
                            getList();
                        } else {
                            $.toast("删除失败!");
                        }
                    }
                })
            })
        })
});