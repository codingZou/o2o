package com.zj.o2o.dao;

import com.zj.o2o.entity.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zj
 * @create 2019-01-24 19:50
 */
public interface ShopCategoryDao {

    /**
     * 查找店铺类别
     *
     * @param shopCategoryCondition
     * @return
     */
    List<ShopCategory> listShopCategory(@Param("shopCategoryCondition") ShopCategory shopCategoryCondition);
}
