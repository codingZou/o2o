package com.zj.o2o.service;

import com.zj.o2o.BaseTest;
import com.zj.o2o.dao.ProductImgDao;
import com.zj.o2o.dto.ImageHolder;
import com.zj.o2o.dto.ProductExecution;
import com.zj.o2o.entity.Product;
import com.zj.o2o.entity.ProductCategory;
import com.zj.o2o.entity.Shop;
import com.zj.o2o.enums.ProductStateEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author zj
 * @create 2019-02-16 18:24
 */
public class ProductServiceImplTest extends BaseTest {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductImgDao productImgDao;

    @Test
    public void insertProduct() throws FileNotFoundException {
        Shop shop = new Shop();
        shop.setShopId(33l);
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(15l);
        Product product = new Product();
        product.setProductId(1l);
        product.setShop(shop);
        product.setProductCategory(productCategory);
        product.setProductName("卫龙辣条");
        product.setProductDesc("非常好吃");
        product.setPriority(20);
        product.setNormalPrice("5元");
        product.setPromotionPrice("2.5元");
        product.setEnableStatus(ProductStateEnum.SUCCESS.getState());
        File thumbnailFile = new File("C:/Users/17935/Desktop/image/latiao_one.png");
        InputStream is = new FileInputStream(thumbnailFile);
        ImageHolder thumbnail = new ImageHolder(thumbnailFile.getName(), is);
        // 创建两个商品详情文件流并将他们加到详情图列表中
        File ProductImg1 = new File("C:/Users/17935/Desktop/image/latiao_one.png");
        InputStream is1 = new FileInputStream(ProductImg1);
        File ProductImg2 = new File("C:/Users/17935/Desktop/image/latiao_two.png");
        InputStream is2 = new FileInputStream(ProductImg2);
        List<ImageHolder> productImgList = new ArrayList<>();
        productImgList.add(new ImageHolder(ProductImg1.getName(), is1));
        productImgList.add(new ImageHolder(ProductImg2.getName(), is2));
        // 添加商品并验证
        ProductExecution pe = productService.insertProduct(product, thumbnail, productImgList);
        assertEquals(ProductStateEnum.SUCCESS.getState(), pe.getState());
    }

    @Test
    public void updateProduct() throws FileNotFoundException {
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(33l);
        product.setShop(shop);
        product.setProductId(28l);
        ProductCategory pc = new ProductCategory();
        pc.setProductCategoryId(17l);
        product.setProductCategory(pc);
        product.setProductName("更新的阿萨姆");
        product.setPriority(502);
        product.setNormalPrice("5");
        product.setPromotionPrice("2.5");
        // 创建缩略图文件流
        File thumbnaiFile = new File("C:/Users/17935/Desktop/image/asm.png");
        InputStream is = new FileInputStream(thumbnaiFile);
        ImageHolder thumbnail = new ImageHolder(thumbnaiFile.getName(), is);
        // 创建两个商品详情图流
        File productImg1 = new File("C:/Users/17935/Desktop/image/asm_desc_one.png");
        InputStream is1 = new FileInputStream(productImg1);
        File productImg2 = new File("C:/Users/17935/Desktop/image/asm_desc_two.png");
        InputStream is2 = new FileInputStream(productImg2);
        List<ImageHolder> productImgList = new ArrayList<>();
        productImgList.add(new ImageHolder(productImg1.getName(), is1));
        productImgList.add(new ImageHolder(productImg2.getName(), is2));
        ProductExecution pe = productService.updateProduct(product, thumbnail, productImgList);
        assertEquals(ProductStateEnum.SUCCESS.getStateInfo(), pe.getStateInfo());
    }
}