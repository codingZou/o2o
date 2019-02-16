package com.zj.o2o.dao;

import com.zj.o2o.BaseTest;
import com.zj.o2o.entity.ProductCategory;
import com.zj.o2o.entity.Shop;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author zj
 * @create 2019-02-15 12:54
 */
public class ProductCategoryDaoTest extends BaseTest {
    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Test
    public void deleteProductCategory() {
        long shopId = 33;
        List<ProductCategory> productCategoryList = productCategoryDao.listProductCategory(shopId);
        for (ProductCategory pc : productCategoryList) {
            if ("服饰".equals(pc.getProductCategoryName()) || "鞋类".equals(pc.getProductCategoryName())) {
                int effectedNum = productCategoryDao.deleteProductCategory(pc.getProductCategoryId(), shopId);
                assertEquals(1, effectedNum);
            }
        }
    }

    @Test
    public void batchInsertProductCategory() {
        ProductCategory productCategory1 = new ProductCategory();
        productCategory1.setCreateTime(new Date());
        productCategory1.setLastEditTime(new Date());
        productCategory1.setProductCategoryName("鞋包");
        productCategory1.setPriority(100);
        productCategory1.setShopId(33l);

        ProductCategory productCategory2 = new ProductCategory();
        productCategory2.setCreateTime(new Date());
        productCategory2.setLastEditTime(new Date());
        productCategory2.setProductCategoryName("鞋包");
        productCategory2.setPriority(100);
        productCategory2.setShopId(33l);
        List<ProductCategory> productCategoryList = new ArrayList<>();
        productCategoryList.add(productCategory1);
        productCategoryList.add(productCategory2);
        int effectedNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
        assertEquals(2, effectedNum);
    }

    @Test
    public void listProductCategory() {
        Shop shop = new Shop();
        shop.setShopId(38l);
        productCategoryDao.listProductCategory(shop.getShopId());
    }
}