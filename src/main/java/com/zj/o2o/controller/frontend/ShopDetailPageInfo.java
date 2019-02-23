package com.zj.o2o.controller.frontend;

import com.zj.o2o.dto.ProductExecution;
import com.zj.o2o.entity.Product;
import com.zj.o2o.entity.ProductCategory;
import com.zj.o2o.entity.Shop;
import com.zj.o2o.service.ProductCategoryService;
import com.zj.o2o.service.ProductService;
import com.zj.o2o.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zj
 * @create 2019-02-23 13:59
 */
@Controller
@RequestMapping("/frontend")
public class ShopDetailPageInfo {
    @Autowired
    private ShopService shopService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 获取店铺信息以及店铺下面的商品类别列表
     *
     * @return
     */
    @RequestMapping(value = "/listshopdetailpageinfo", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> listshopdetailpageinfo(Long shopId) {
        Map<String, Object> modelMap = new HashMap<>();
        if (shopId > 0) {
            // 根据店铺id获取店铺的信息
            Shop shop = shopService.getShopById(shopId);
            // 获取该店铺的商品类别列表
            List<ProductCategory> productCategoryList = productCategoryService.listProductCategory(shopId);
            modelMap.put("shop", shop);
            modelMap.put("productCategoryList", productCategoryList);
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty shopId");
        }
        return modelMap;
    }

    /**
     * 条件分页查询商品列表
     *
     * @param pageIndex
     * @param pageSize
     * @param productCondition
     * @return
     */
    @RequestMapping(value = "/listproductbyshop", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> listproductbyshop(int pageIndex, int pageSize, Product productCondition) {
        Map<String, Object> modelMap = new HashMap<>();
        if (pageIndex > -1 && pageSize >= 1 && productCondition != null
                && productCondition.getShop() != null && productCondition.getShop().getShopId() > -1) {
            productCondition.setEnableStatus(1);
            ProductExecution pe = productService.listProduct(productCondition, pageIndex, pageSize);
            modelMap.put("productList", pe.getProductList());
            modelMap.put("count", pe.getCount());
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageIndex or pageSize or shopId");
        }
        return modelMap;
    }
}
