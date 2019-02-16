package com.zj.o2o.controller.shopadmin;

import com.zj.o2o.dto.ProductCategoryExecution;
import com.zj.o2o.entity.ProductCategory;
import com.zj.o2o.entity.Shop;
import com.zj.o2o.enums.ProductCategoryStateEnum;
import com.zj.o2o.enums.ShopStateEnum;
import com.zj.o2o.exceptions.ProductCategoryOperationException;
import com.zj.o2o.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zj
 * @create 2019-02-15 13:10
 */
@Controller
@RequestMapping(value = "/shopadmin")
public class ProductCategoryController {
    @Autowired
    private ProductCategoryService productCategoryService;

    @RequestMapping(value = "/deleteproductcategory", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteproductcategory(HttpServletRequest request, Long productCategoryId) {
        Map<String, Object> modelMap = new HashMap<>();
        if (productCategoryId != null && productCategoryId > 0) {
            try {
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                ProductCategoryExecution pe = productCategoryService.deleteProductCategory(productCategoryId, currentShop.getShopId());
                if (pe.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (ProductCategoryOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请选择要删除的商品类别");
        }
        return modelMap;
    }

    @RequestMapping(value = "/getproductcategorylist", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getProductCategoryList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        // Todo be removed
        /*Shop shop = new Shop();
        shop.setShopId(38l);
        request.getSession().setAttribute("currentShop", shop);*/
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        if (currentShop != null && currentShop.getShopId() > 0) {
            try {
                List<ProductCategory> productCategoryList = productCategoryService.listProductCategory(currentShop.getShopId());
                modelMap.put("productCategoryList", productCategoryList);
                modelMap.put("success", true);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", ShopStateEnum.INNER_ERROR.getStateInfo());
        }
        return modelMap;
    }

    @RequestMapping(value = "/addproductcategory", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addproductcategory(HttpServletRequest request, @RequestBody List<ProductCategory> productCategoryList) {
        Map<String, Object> modelMap = new HashMap<>();
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        for (ProductCategory pc : productCategoryList) {
            pc.setShopId(currentShop.getShopId());
        }
        if (productCategoryList != null && productCategoryList.size() > 0) {
            try {
                ProductCategoryExecution pe = productCategoryService.batchInsertProductCategory(productCategoryList);
                if (pe.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (ProductCategoryOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入至少一个商品类别");
        }
        return modelMap;
    }
}
