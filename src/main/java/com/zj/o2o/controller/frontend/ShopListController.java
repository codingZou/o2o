package com.zj.o2o.controller.frontend;

import com.zj.o2o.dto.ShopExecution;
import com.zj.o2o.entity.Area;
import com.zj.o2o.entity.Shop;
import com.zj.o2o.entity.ShopCategory;
import com.zj.o2o.service.AreaService;
import com.zj.o2o.service.ShopCategoryService;
import com.zj.o2o.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zj
 * @create 2019-02-22 13:46
 */
@Controller
@RequestMapping(value = "/frontend")
public class ShopListController {
    @Autowired
    private AreaService areaService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private ShopService shopService;

    @RequestMapping(value = "/listshoppageinfo")
    @ResponseBody
    public Map<String, Object> getShopListPageInfo(HttpServletRequest request, Long parentId) {
        Map<String, Object> modelMap = new HashMap<>();
        List<ShopCategory> shopCategoryList = null;
        if (parentId != null && parentId > -1) {
            // 如果parentId 存在，则取出该一级ShopCategory下的二级ShopCategory列表
            try {
                ShopCategory parent = new ShopCategory();
                parent.setShopCategoryId(parentId);
                ShopCategory shopCategoryCondition = new ShopCategory();
                shopCategoryCondition.setParent(parent);
                shopCategoryList = shopCategoryService.listShopCategory(shopCategoryCondition);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
        } else {
            // 如果parentId不存在，则取出所有一级ShopCategory(全部商店列表)
            try {
                shopCategoryList = shopCategoryService.listShopCategory(null);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
        }
        modelMap.put("shopCategoryList", shopCategoryList);
        List<Area> areaList = null;
        // 获取区域列表信息
        try {
            areaList = areaService.listArea();
            modelMap.put("success", true);
            modelMap.put("areaList", areaList);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }

    /**
     * （分页）获取指定查询条件下的店铺列表
     *
     * @param pageIndex     从第几页开始
     * @param pageSize      每页大小
     * @param shopCondition 二级id parentId 二级类别id  shopCategroyId 区域Id areaId shopName 模糊查询条件店铺名
     * @return
     */
    @RequestMapping(value = "/listshop", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> listShop(int pageIndex, int pageSize, Shop shopCondition, Long parentId) {
        Map<String, Object> modelMap = new HashMap<>();
        // 获取页码
        if (pageIndex >= -1 && pageSize > -1) {
            shopCondition.setEnableStatus(1);
//            ShopCategory childCategory = new ShopCategory();
            ShopCategory parentCategory = new ShopCategory();
            parentCategory.setShopCategoryId(parentId);
            shopCondition.getShopCategory().setParent(parentCategory);
//            shopCondition.setShopCategory(childCategory);
            ShopExecution se = shopService.getListShop(shopCondition, pageIndex, pageSize);
            modelMap.put("shopList", se.getShopList());
            modelMap.put("count", se.getCount());
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIndex");
        }
        return modelMap;
    }
}
