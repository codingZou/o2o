package com.zj.o2o.service;

import com.zj.o2o.dto.ShopExecution;
import com.zj.o2o.entity.Shop;

import java.io.InputStream;

/**
 * @author zj
 * @create 2019-01-22 18:47
 */
public interface ShopService {
    ShopExecution getListShop(Shop shopCondition, int pageIndex, int pageSize);

    ShopExecution addShop(Shop shop, InputStream shopImgInputStream, String fileName);

    Shop getShopById(Long shopId);

    ShopExecution updateShop(Shop shop, InputStream shopImgInputStream, String fileName);
}
