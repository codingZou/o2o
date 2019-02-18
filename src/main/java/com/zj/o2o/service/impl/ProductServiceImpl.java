package com.zj.o2o.service.impl;

import com.zj.o2o.dao.ProductDao;
import com.zj.o2o.dao.ProductImgDao;
import com.zj.o2o.dto.ImageHolder;
import com.zj.o2o.dto.ProductExecution;
import com.zj.o2o.entity.Product;
import com.zj.o2o.entity.ProductImg;
import com.zj.o2o.enums.ProductStateEnum;
import com.zj.o2o.exceptions.ProductOperationException;
import com.zj.o2o.service.ProductService;
import com.zj.o2o.util.ImageUtil;
import com.zj.o2o.util.PageCalculator;
import com.zj.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zj
 * @create 2019-02-16 17:22
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImgDao productImgDao;

    @Override
    public ProductExecution listProduct(Product productCondition, int pageIndex, int pageSize) {
        // 页码转换成数据库的行数
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
        List<Product> listProduct = productDao.listProduct(productCondition, rowIndex, pageSize);
        int productCount = productDao.getProductCount(productCondition);
        ProductExecution pe = new ProductExecution();
        if (listProduct.size() > -1) {
            pe.setProductList(listProduct);
            pe.setCount(productCount);
            pe.setState(ProductStateEnum.SUCCESS.getState());
        } else {
            pe.setState(ProductStateEnum.INNER_ERROR.getState());
        }
        return pe;
    }

    @Transactional
    @Override
    public ProductExecution insertProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList) throws ProductOperationException {
        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
            // 给商品设置默认属性
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
            product.setEnableStatus(1);
            if (thumbnail != null) {
                addThumbnail(product, thumbnail);
            }
            try {
                // 创建商品信息
                int effectedNum = productDao.insertProduct(product);
                if (effectedNum <= 0) {
                    throw new ProductOperationException("商品创建失败");
                }
            } catch (ProductOperationException e) {
                throw new ProductOperationException("商品创建失败:" + e.getMessage());
            }
            // 商品详情图不为空
            if (productImgList != null && productImgList.size() > 0) {
                addProductImgList(product, productImgList);
            }
            return new ProductExecution(ProductStateEnum.SUCCESS, product);
        } else {
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    @Override
    public Product getProductById(long productId) {
        return productDao.getProductByid(productId);
    }

    @Transactional
    @Override
    public ProductExecution updateProduct(Product product, ImageHolder thumbnail,
                                          List<ImageHolder> productImgList) throws ProductOperationException {
        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
            product.setLastEditTime(new Date());
            if (thumbnail != null) {
                // 先判断原商品有没有图片，有则删除，然后添加新图片
                Product tempProduct = productDao.getProductByid(product.getProductId());
                if (tempProduct.getImgAddr() != null) {
                    ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
                }
                // 添加新图片
                addThumbnail(product, thumbnail);
            }
            if (productImgList != null && productImgList.size() > 0) {
                // 删除原来的图片
                deleteProductImgList(product.getProductId());
                // 添加新的详情图片
                addProductImgList(product, productImgList);
            }
            try {
                int effectNum = productDao.updateProduct(product);
                if (effectNum <= 0) {
                    throw new ProductOperationException("更新商品信息失败");
                }
                return new ProductExecution(ProductStateEnum.SUCCESS, product);
            } catch (Exception e) {
                throw new ProductOperationException("更新商品信息失败" + e.getMessage());
            }
        } else {
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    /**
     * 删除某个商品下的所有详情图
     *
     * @param productId
     */
    private void deleteProductImgList(Long productId) {
        List<ProductImg> productImgList = productImgDao.listProductImg(productId);
        // 删除路径下的图片
        for (ProductImg productImg : productImgList) {
            ImageUtil.deleteFileOrPath(productImg.getImgAddr());
        }
        // 删除数据库里原有图片信息记录
        productImgDao.deleteProductImgByProductId(productId);
    }

    /**
     * 添加商品详情图
     *
     * @param product
     * @param productImgHolderList
     */
    private void addProductImgList(Product product, List<ImageHolder> productImgHolderList) {
        // 获取图片存储路径，直接存放到想要店铺的文件夹底下
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        List<ProductImg> productImgList = new ArrayList<>();
        // 遍历图片一次去处理，并添加到productImg实体类里
        for (ImageHolder productImgHolder : productImgHolderList) {
            String imgAddr = ImageUtil.generateNormalImg(productImgHolder, dest);
            ProductImg productImg = new ProductImg();
            productImg.setImgAddr(imgAddr);
            productImg.setProductId(product.getProductId());
            productImg.setCreateTime(new Date());
            productImgList.add(productImg);
        }
        if (productImgHolderList.size() > 0) {
            try {
                int effectedNum = productImgDao.batchInsertProductImg(productImgList);
                if (effectedNum <= 0) {
                    throw new ProductOperationException("添加商品详情图失败");
                }
            } catch (ProductOperationException e) {
                throw new ProductOperationException("添加商品详情图失败:" + e.getMessage());
            }
        }
    }

    /**
     * 添加缩略图
     *
     * @param product
     * @param thumbnail
     */
    private void addThumbnail(Product product, ImageHolder thumbnail) {
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        String thumbnail1 = ImageUtil.generateThumbnail(thumbnail, dest);
        product.setImgAddr(thumbnail1);
    }
}
