package com.zj.o2o.dao;

import com.zj.o2o.entity.ProductImg;

import java.util.List;

/**
 * @author zj
 * @create 2019-02-16 14:38
 */
public interface ProductImgDao {
    /**
     * 批量添加商品详情图片
     *
     * @param productImgList
     * @return
     */
    int batchInsertProductImg(List<ProductImg> productImgList);

    int deleteProductImgByProductId(long productId);

    List<ProductImg> listProductImg(long productId);
}
