package com.zj.o2o.dao;

import com.zj.o2o.BaseTest;
import com.zj.o2o.entity.Area;
import com.zj.o2o.entity.PersonInfo;
import com.zj.o2o.entity.Shop;
import com.zj.o2o.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.assertEquals;


/**
 * @author zj
 * @create 2019-01-18 15:46
 */
public class ShopDaoTest extends BaseTest {
    @Autowired
    private ShopDao shopDao;

    @Test
    public void getShopByID() {
        Shop shop = shopDao.getShopByID(33l);
        System.out.println(shop.getArea().getAreaId() + shop.getArea().getAreaName());
    }

    @Test
    public void insertShop() {
        Shop shop = new Shop();
        Area area = new Area();
        PersonInfo owner = new PersonInfo();
        ShopCategory shopCategory = new ShopCategory();
        area.setAreaId(2);
        owner.setUserId(1L);
        shopCategory.setShopCategoryId(35L);
        shop.setArea(area);
        shop.setOwner(owner);
        shop.setShopCategory(shopCategory);
        shop.setShopName("测试店铺");
        shop.setShopDesc("test");
        shop.setShopAddr("test");
        shop.setCreateTime(new Date());
        shop.setShopImg("test");
        shop.setEnableStatus(1);
        shop.setAdvice("审核中");
        //影响行数
        int effectedNum = shopDao.insertShop(shop);
        assertEquals(1, effectedNum);

    }

    @Test
    public void updateShop() {
        Shop shop = new Shop();
        shop.setShopId(29L);
        shop.setShopName("测试更新");
        shop.setShopAddr("测试更新");
        shop.setShopDesc("测试更新");
        shop.setLastEditTime(new Date());
        int effectedNum = shopDao.updateShop(shop);
        assertEquals(1, effectedNum);
    }
}