package com.zj.o2o.service;

import com.zj.o2o.entity.ShopCategory;

import java.util.List;

/**
 * @author zj
 * @create 2019-01-24 21:25
 */
public interface ShopCategoryService {
    List<ShopCategory> listShopCategory(ShopCategory shopCategoryCondition);
}
