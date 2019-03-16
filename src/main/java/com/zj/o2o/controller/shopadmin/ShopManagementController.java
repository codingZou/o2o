package com.zj.o2o.controller.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zj.o2o.dto.ImageHolder;
import com.zj.o2o.dto.ShopExecution;
import com.zj.o2o.entity.Area;
import com.zj.o2o.entity.PersonInfo;
import com.zj.o2o.entity.Shop;
import com.zj.o2o.entity.ShopCategory;
import com.zj.o2o.enums.ShopStateEnum;
import com.zj.o2o.service.AreaService;
import com.zj.o2o.service.ShopCategoryService;
import com.zj.o2o.service.ShopService;
import com.zj.o2o.util.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zj
 * @create 2019-01-24 14:48
 */
@Controller
@RequestMapping(value = "/shopadmin")
public class ShopManagementController {
    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private AreaService areaService;

    /**
     * 更新店铺信息
     *
     * @param request
     * @param shopImg
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateshop", method = {RequestMethod.POST})
    public Map<String, Object> updateShop(HttpServletRequest request, @RequestParam(value = "shopImg")
            CommonsMultipartFile shopImg) {
        Map<String, Object> modelMap = new HashMap<>();
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码错误!");
            return modelMap;
        }
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        String shopStr = request.getParameter("shopStr");
        try {
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        // 修改店铺信息
        if (shop != null && shop.getShopId() != null) {
            ShopExecution se = null;
            if (shopImg == null) {
                se = shopService.updateShop(shop, null);
            } else {
                ImageHolder imageHolder = null;
                try {
                    imageHolder = new ImageHolder(shopImg.getOriginalFilename(), shopImg.getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                se = shopService.updateShop(shop, imageHolder);
            }
            if (se.getState() == ShopStateEnum.SUCCESS.getState()) {
                modelMap.put("success", true);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", se.getStateInfo());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入店铺Id");
        }
        return modelMap;
    }

    /**
     * 根据id查询店铺信息
     *
     * @param shopId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getshopbyid", method = {RequestMethod.GET})
    public Map<String, Object> getShopById(Long shopId) {
        Map<String, Object> modelMap = new HashMap<>();
        if (shopId > -1) {
            try {
                List<Area> areaList = areaService.listArea();
                Shop shop = shopService.getShopById(shopId);
                modelMap.put("areaList", areaList);
                modelMap.put("shop", shop);
                modelMap.put("success", true);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty shopId");
        }
        return modelMap;
    }

    /**
     * 注册店铺信息
     *
     * @param request
     * @param shopImg
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/registershop", method = {RequestMethod.POST})
    public Map<String, Object> registerShop(HttpServletRequest request, @RequestParam(value = "shopImg")
            CommonsMultipartFile shopImg) {
        Map<String, Object> modelMap = new HashMap<>();
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码错误!");
            return modelMap;
        }
        if (shopImg == null) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "上传图片不能为空");
            return modelMap;
        }
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        String shopStr = request.getParameter("shopStr");
//        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        /*// 获取上传的图片
        MultipartHttpServletRequest multipartRequest = null;
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request)) {
            multipartRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartRequest
                    .getFile("shopImg");
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "上传图片不能为空");
            return modelMap;
        }*/
        try {
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        if (shop != null && shopImg != null) {
            PersonInfo owner = (PersonInfo) request.getSession().getAttribute("user");
            shop.setOwner(owner);
            ShopExecution se = null;
            ImageHolder imageHolder = null;
            try {
                imageHolder = new ImageHolder(shopImg.getOriginalFilename(), shopImg.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            se = shopService.addShop(shop, imageHolder);
            if (se.getState() == ShopStateEnum.CHECK.getState()) {
                modelMap.put("success", true);
                // 该用户可以操作的店铺列表
                List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
                if (shopList == null || shopList.size() == 0) {
                    shopList = new ArrayList<>();
                }
                shopList.add(se.getShop());
                request.getSession().setAttribute("shopList", shopList);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", se.getStateInfo());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入店铺信息");
        }
        return modelMap;
    }

    /**
     * 获取店铺类别和区域列表
     *
     * @return
     */
    @RequestMapping(value = "/getshopinitinfo", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> getShopInitInfo() {
        Map<String, Object> map = new HashMap<>();
        List<ShopCategory> shopCategoryList = null;
        List<Area> areaList = null;
        try {
            //获取parentId不为空的二级类别
            shopCategoryList = shopCategoryService.listShopCategory(new ShopCategory());
            areaList = areaService.listArea();
            map.put("shopCategoryList", shopCategoryList);
            map.put("shopAreaList", areaList);
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
            map.put("errMsg", e.getMessage());
        }
        return map;
    }

    /**
     * 获取用户的店铺列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getshoplist", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getShopList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        // TODO
        /*PersonInfo user = new PersonInfo();
        user.setUserId(12L);
        user.setName("张三");
        request.getSession().setAttribute("user", user);*/
        PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
        if (user != null) {
            try {
                Shop shopCondition = new Shop();
                shopCondition.setOwner(user);
                ShopExecution se = shopService.getListShop(shopCondition, 0, 100);
                modelMap.put("shopList", se.getShopList());
                modelMap.put("success", true);
                modelMap.put("user", user);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "session is null");
        }
        return modelMap;
    }

    /**
     * 查询店铺详情信息
     *
     * @param request
     * @param shopId
     * @return
     */
    @RequestMapping(value = "getshopmanagementinfo", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getShopManagementInfo(HttpServletRequest request, @RequestParam(value = "shopId") long shopId) {
        Map<String, Object> modelMap = new HashMap<>();
//        long shopId = Long.parseLong(request.getParameter("shopId"));
        if (shopId <= 0) {
            Object currentShopObj = request.getSession().getAttribute("currentShop");
            if (currentShopObj == null) {
                modelMap.put("redirect", true);
                modelMap.put("url", "/o2o/shopadmin/shopList");
            } else {
                Shop currentShop = (Shop) currentShopObj;
                modelMap.put("redirect", false);
                modelMap.put("shopId", currentShop.getShopId());
            }
        } else {
            Shop currentShop = new Shop();
            currentShop.setShopId(shopId);
            // 将当前操作的店铺存入session中
            request.getSession().setAttribute("currentShop", currentShop);
            modelMap.put("redirect", false);
        }
        return modelMap;
    }
}
