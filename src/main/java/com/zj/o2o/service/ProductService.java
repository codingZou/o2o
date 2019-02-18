package com.zj.o2o.service;

import com.zj.o2o.dto.ImageHolder;
import com.zj.o2o.dto.ProductExecution;
import com.zj.o2o.entity.Product;
import com.zj.o2o.exceptions.ProductOperationException;

import java.util.List;

/**
 * @author zj
 * @create 2019-02-16 16:45
 */
public interface ProductService {
    ProductExecution listProduct(Product productCondition, int pageIndex, int pageSize);

    /**
     * 添加商品信息及图片处理
     *
     * @param product
     * @param thumbnail
     * @param productImgList
     * @return
     * @throws ProductOperationException
     */
    ProductExecution insertProduct(Product product, ImageHolder thumbnail,
                                   List<ImageHolder> productImgList) throws ProductOperationException;

    Product getProductById(long productId);

    ProductExecution updateProduct(Product product, ImageHolder thumbnail,
                                   List<ImageHolder> productImgList) throws ProductOperationException;
}
