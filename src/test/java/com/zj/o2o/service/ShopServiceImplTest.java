package com.zj.o2o.service;

import com.zj.o2o.BaseTest;
import com.zj.o2o.dto.ShopExecution;
import com.zj.o2o.entity.Area;
import com.zj.o2o.entity.PersonInfo;
import com.zj.o2o.entity.Shop;
import com.zj.o2o.enums.ShopStateEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;


/**
 * @author zj
 * @create 2019-01-22 19:46
 */
public class ShopServiceImplTest extends BaseTest {
    @Autowired
    private ShopService shopService;

    @Test
    public void addShop() throws FileNotFoundException {
        Shop shop = new Shop();
        Area area = new Area();
        PersonInfo Owner = new PersonInfo();
        Owner.setUserId(12L);
        area.setAreaId(7);
        shop.setArea(area);
        shop.setOwner(Owner);
        shop.setShopName("开心饭店3");
        shop.setShopAddr("深圳市宝安区");
        shop.setPhone("112110");
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setAdvice("审核中");
        File shopImg = new File("C:/Users/17935/Desktop/image/five.png");
        InputStream is = new FileInputStream(shopImg);
        ShopExecution shopExecution = shopService.addShop(shop, is, shopImg.getName());
        assertEquals(ShopStateEnum.CHECK.getState(), shopExecution.getState());
    }

    @Test
    public void updateShop() throws FileNotFoundException {
        Shop shop = new Shop();
        shop.setShopId(38L);
        shop.setShopName("测试更新店铺2");
        File shopImg = new File("C:/Users/17935/Desktop/image/two.png");
        InputStream is = new FileInputStream(shopImg);
        ShopExecution updateShop = shopService.updateShop(shop, is, shopImg.getName());
        System.out.println("shop图片地址为：" + updateShop.getShop().getShopImg());
    }
}