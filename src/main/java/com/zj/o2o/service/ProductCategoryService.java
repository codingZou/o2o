package com.zj.o2o.service;

import com.zj.o2o.dto.ProductCategoryExecution;
import com.zj.o2o.entity.ProductCategory;

import java.util.List;

/**
 * @author zj
 * @create 2019-02-15 13:08
 */
public interface ProductCategoryService {
    List<ProductCategory> listProductCategory(long shopId);

    /**
     * 批量新增商品类别
     *
     * @param ProductCategoryList
     * @return
     */
    ProductCategoryExecution batchInsertProductCategory(List<ProductCategory> ProductCategoryList);

    ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId);
}
