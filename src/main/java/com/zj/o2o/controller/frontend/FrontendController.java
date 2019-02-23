package com.zj.o2o.controller.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/frontend")
public class FrontendController {
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    private String index() {
        // 转发到前端首页
        return "frontend/index";
    }

    @RequestMapping(value = "/shoplist", method = RequestMethod.GET)
    private String showShopList() {
        // 转发到店铺列表
        return "frontend/shoplist";
    }

    @RequestMapping(value = "/shopdetail", method = RequestMethod.GET)
    private String showShopDetail() {
        // 转发到店铺详细信息
        return "frontend/shopdetail";
    }
    /*@RequestMapping(value = "/mainpage", method = RequestMethod.GET)
    private String showMainPage() {
        return "frontend/mainpage";
    }

    @RequestMapping(value = "/productdetail", method = RequestMethod.GET)
    private String showProductDetail() {
        return "frontend/productdetail";
    }

    @RequestMapping(value = "/shopdetail", method = RequestMethod.GET)
    private String showShopDetail() {
        return "frontend/shopdetail";
    }

    @RequestMapping(value = "/mypoint", method = RequestMethod.GET)
    private String myPoint() {
        return "frontend/mypoint";
    }

    @RequestMapping(value = "/myrecord", method = RequestMethod.GET)
    private String myRecord() {
        return "frontend/myrecord";
    }

    @RequestMapping(value = "/pointrecord", method = RequestMethod.GET)
    private String pointRecord() {
        return "frontend/pointrecord";
    }

    @RequestMapping(value = "/awarddetail", method = RequestMethod.GET)
    private String awardDetail() {
        return "frontend/awarddetail";
    }

    @RequestMapping(value = "/customerbind", method = RequestMethod.GET)
    private String customerBind() {
        return "frontend/customerbind";
    }*/
}
