package com.zj.o2o.service.impl;

import com.zj.o2o.dao.ShopCategoryDao;
import com.zj.o2o.entity.ShopCategory;
import com.zj.o2o.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zj
 * @create 2019-01-24 21:27
 */
@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {
    @Autowired
    private ShopCategoryDao shopCategoryDao;

    /**
     * 查询店铺类别信息
     *
     * @param shopCategoryCondition
     * @return
     */
    @Override
    public List<ShopCategory> listShopCategory(ShopCategory shopCategoryCondition) {

        return shopCategoryDao.listShopCategory(shopCategoryCondition);
    }

}
