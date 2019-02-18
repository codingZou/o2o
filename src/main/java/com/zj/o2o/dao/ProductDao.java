package com.zj.o2o.dao;

import com.zj.o2o.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zj
 * @create 2019-02-16 14:38
 */
public interface ProductDao {
    /**
     * （条件）分页查询商品列表 可输入的条件有：商品名，商品状态，店铺Id,商品类别
     *
     * @param productCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<Product> listProduct(@Param("productCondition") Product productCondition,
                              @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

    /**
     * 条件查询商品的总数
     *
     * @param productCondition
     * @return
     */
    int getProductCount(@Param("productCondition") Product productCondition);

    /**
     * 添加商品
     *
     * @param product
     * @return
     */
    int insertProduct(Product product);

    /**
     * 查询商品信息根据productId
     *
     * @param productId
     * @return
     */
    Product getProductByid(Long productId);

    /**
     * 更新商品信息
     *
     * @param product
     * @return
     */
    int updateProduct(Product product);

    /**
     * 删除商品类别之前，将商品的类别的id置空
     * @param productCategoryId
     * @return
     */
    int updateProductCategoryToNull(long productCategoryId);

}
