package com.zj.o2o.dao;

import com.zj.o2o.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zj
 * @create 2019-02-15 12:44
 */
public interface ProductCategoryDao {
    /**
     * 查询商品类别
     *
     * @param shopId
     * @return
     */
    List<ProductCategory> listProductCategory(long shopId);

    /**
     * 批量新增商品类别
     *
     * @param ProductCategoryList
     * @return
     */
    int batchInsertProductCategory(List<ProductCategory> ProductCategoryList);

    /**
     * 删除指定商品类别
     *
     * @param productCategoryId
     * @param shopId
     * @return
     */
    int deleteProductCategory(@Param("productCategoryId") long productCategoryId, @Param("shopId") long shopId);
}
