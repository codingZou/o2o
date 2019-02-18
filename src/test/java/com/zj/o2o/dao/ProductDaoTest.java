package com.zj.o2o.dao;

import com.zj.o2o.BaseTest;
import com.zj.o2o.entity.Product;
import com.zj.o2o.entity.ProductCategory;
import com.zj.o2o.entity.Shop;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author zj
 * @create 2019-02-16 15:24
 */
public class ProductDaoTest extends BaseTest {
    @Autowired
    private ProductDao productDao;

    @Test
    public void listProduct() {
        Product productCondition = new Product();
        ProductCategory pc = new ProductCategory();
        pc.setProductCategoryId(17l);
        List<Product> listProduct = productDao.listProduct(productCondition, 0, 4);
        assertEquals(4, listProduct.size());
    }

    @Test
    public void getProductCount() {
        Product productCondition = new Product();
        productCondition.setEnableStatus(1);
        int productCount = productDao.getProductCount(productCondition);
        assertEquals(3, productCount);
    }

    @Test
    public void getProductByid() {
        long productId = 28l;
        Product product = productDao.getProductByid(productId);
        assertEquals("卫龙辣条", product.getProductName());
    }

    @Test
    public void insertProduct() {
        Product product = new Product();
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(15l);
        product.setProductCategory(productCategory);
        Shop shop = new Shop();
        shop.setShopId(33l);
        product.setShop(shop);
        product.setProductName("卫龙辣条");
        product.setImgAddr("测试图片地址");
        product.setPriority(10);
        product.setNormalPrice("5元");
        product.setPromotionPrice("2.5元");
        product.setCreateTime(new Date());
        product.setEnableStatus(1);
        int effectNum = productDao.insertProduct(product);
        assertEquals(1, effectNum);
    }

    @Test
    public void updateProduct() {
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(33l);
        product.setShop(shop);
        product.setProductId(28l);
        ProductCategory pc = new ProductCategory();
        pc.setProductCategoryId(17l);
        product.setProductCategory(pc);
        product.setProductName("阿萨姆");
        product.setPriority(50);
        product.setNormalPrice("5");
        product.setPromotionPrice("2.5");
        int effectNum = productDao.updateProduct(product);
        assertEquals(1, effectNum);
    }

}