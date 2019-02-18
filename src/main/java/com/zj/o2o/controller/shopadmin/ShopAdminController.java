package com.zj.o2o.controller.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author zj
 * @create 2019-01-24 18:23
 */
@Controller
@RequestMapping(value = "/shopadmin", method = {RequestMethod.GET})
public class ShopAdminController {
    @RequestMapping(value = "/shopoperation")
    public String shopOperation() {
        // 转发到店铺编辑或注册页面
        return "shop/shopoperation";
    }

    @RequestMapping(value = "/shoplist")
    public String shopList() {
        // 转发到店铺列表页面
        return "shop/shoplist";
    }

    @RequestMapping(value = "/shopmanagement")
    public String shopManagement() {
        // 转发到店铺管理页面
        return "shop/shopmanagement";
    }

    @RequestMapping(value = "/productcategorymanage")
    public String productCategorymanage() {
        // 转发至商品类别管理页面
        return "shop/productcategorymanage";
    }

    @RequestMapping(value = "/productmanage")
    public String productManage() {
        // 转发至商品管理页面
        return "shop/productmanage";
    }

    @RequestMapping(value = "/productoperation")
    public String productOperation() {
        // 转发至商品添加/编辑页面
        return "shop/productoperation";
    }
}
