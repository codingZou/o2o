package com.zj.o2o.dao;

import com.zj.o2o.BaseTest;
import com.zj.o2o.entity.ProductImg;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author zj
 * @create 2019-02-16 15:40
 */
public class ProductImgDaoTest extends BaseTest {
    @Autowired
    private ProductImgDao productImgDao;

    @Test
    public void deleteProductImgByProductId() {
        long productId = 29l;
        int offecteNum = productImgDao.deleteProductImgByProductId(productId);
        assertEquals(1, offecteNum);
    }

    @Test
    public void batchInsertProductImg() {
        ProductImg productImg1 = new ProductImg();
        productImg1.setImgAddr("图片地址");
        productImg1.setImgDesc("图片说明");
        productImg1.setCreateTime(new Date());
        productImg1.setPriority(1);
        productImg1.setProductId(28l);
        ProductImg productImg2 = new ProductImg();
        productImg2.setImgAddr("图片地址2");
        productImg2.setImgDesc("图片说明2");
        productImg2.setCreateTime(new Date());
        productImg2.setPriority(2);
        productImg2.setProductId(28l);
        List<ProductImg> productImgList = new ArrayList<>();
        productImgList.add(productImg1);
        productImgList.add(productImg2);
        int offectedNum = productImgDao.batchInsertProductImg(productImgList);
        assertEquals(2, offectedNum);
    }

}