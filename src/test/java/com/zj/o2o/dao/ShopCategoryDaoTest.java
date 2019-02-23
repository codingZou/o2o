package com.zj.o2o.dao;

import com.zj.o2o.BaseTest;
import com.zj.o2o.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author zj
 * @create 2019-01-24 20:54
 */
public class ShopCategoryDaoTest extends BaseTest {
    @Autowired
    private ShopCategoryDao shopCategoryDao;

    @Test
    public void ListShopCategory() {
//        ShopCategory shopCategory = new ShopCategory();
        List<ShopCategory> shopCategoryList = shopCategoryDao.listShopCategory(null);
        System.out.println(shopCategoryList.size());
        /*ShopCategory parentCategory = new ShopCategory();
        parentCategory.setShopCategoryId(33L);
        shopCategory.setParent(parentCategory);
        List<ShopCategory> shopCategories = shopCategoryDao.listShopCategory(shopCategory);
        assertEquals(4, shopCategoryList.size());
        System.out.println(shopCategories.get(0).getShopCategoryName());
        assertEquals(1, shopCategories.size());*/
    }

}