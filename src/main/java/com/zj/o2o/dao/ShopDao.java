package com.zj.o2o.dao;

import com.zj.o2o.entity.Shop;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zj
 * @create 2019-01-18 15:04
 */
@Repository
public interface ShopDao {
    /**
     * listShop的总数
     *
     * @param shopCondition
     * @return
     */
    int getShopCount(@Param("shopCondition") Shop shopCondition);

    /**
     * 分页查询店铺，条件：店铺名(模糊)，店铺状态，店铺类别，区域id，owner
     *
     * @param shopCondition 查询条件
     * @param rowIndex      从第几行开始取数据
     * @param pageSize      返回的条数
     * @return
     */
    List<Shop> listShop(@Param("shopCondition") Shop shopCondition,
                        @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

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
