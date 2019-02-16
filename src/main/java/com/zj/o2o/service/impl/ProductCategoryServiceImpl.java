package com.zj.o2o.service.impl;

import com.zj.o2o.dao.ProductCategoryDao;
import com.zj.o2o.dto.ProductCategoryExecution;
import com.zj.o2o.entity.ProductCategory;
import com.zj.o2o.enums.ProductCategoryStateEnum;
import com.zj.o2o.exceptions.ProductCategoryOperationException;
import com.zj.o2o.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zj
 * @create 2019-02-15 13:09
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    private ProductCategoryDao productCategoryDao;

    /**
     * 获取商品类别列表
     *
     * @param shopId
     * @return
     */
    @Override
    public List<ProductCategory> listProductCategory(long shopId) {
        return productCategoryDao.listProductCategory(shopId);
    }

    @Transactional
    @Override
    public ProductCategoryExecution batchInsertProductCategory(List<ProductCategory> ProductCategoryList)
            throws ProductCategoryOperationException {
        int effectedNum = productCategoryDao.batchInsertProductCategory(ProductCategoryList);
        if (ProductCategoryList != null && ProductCategoryList.size() > 0) {
            try {
                if (effectedNum <= 0) {
                    throw new ProductCategoryOperationException("店铺类别创建失败");
                } else {
                    return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
                }
            } catch (Exception e) {
                throw new ProductCategoryOperationException("batchInsertProductCategory error:" + e.getMessage());
            }
        } else {
            return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
        }
    }

    @Override
    @Transactional
    public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId) {
        //TODO 将此商品类别下的商品id置为空
        try {
            int effectedNum = productCategoryDao.deleteProductCategory(productCategoryId, shopId);
            if (effectedNum <= 0) {
                throw new ProductCategoryOperationException("店铺商品类别删除失败");
            } else {
                return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
            }
        } catch (Exception e) {
            throw new ProductCategoryOperationException("deleteProductCategoey error:" + e.getMessage());
        }
    }
}
