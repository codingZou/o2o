package com.zj.o2o.dao;

import com.zj.o2o.entity.Shop;
import org.springframework.stereotype.Repository;

/**
 * @author zj
 * @create 2019-01-18 15:04
 */
@Repository
public interface ShopDao {
    /**
     * 通过shopId查询店铺
     *
     * @param shopId
     * @return
     */
    Shop getShopByID(Long shopId);

    /**
     * 新增店铺
     *
     * @param shop
     * @return
     */
    int insertShop(Shop shop);

    /**
     * 更新店铺
     *
     * @param shop
     * @return
     */
    int updateShop(Shop shop);
}
