package com.zj.o2o.service.impl;

import com.zj.o2o.dao.ShopDao;
import com.zj.o2o.dto.ImageHolder;
import com.zj.o2o.dto.ShopExecution;
import com.zj.o2o.entity.Shop;
import com.zj.o2o.enums.ShopStateEnum;
import com.zj.o2o.exceptions.ShopOperationException;
import com.zj.o2o.service.ShopService;
import com.zj.o2o.util.ImageUtil;
import com.zj.o2o.util.PageCalculator;
import com.zj.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author zj
 * @create 2019-01-22 18:50
 */
@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ShopDao shopDao;

    @Override
    public ShopExecution getListShop(Shop shopCondition, int pageIndex, int pageSize) {
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
        List<Shop> shopList = shopDao.listShop(shopCondition, rowIndex, pageSize);
        int count = shopDao.getShopCount(shopCondition);
        ShopExecution se = new ShopExecution();
        if (shopList != null) {
            se.setShopList(shopList);
            se.setCount(count);
        } else {
            se.setState(ShopStateEnum.INNER_ERROR.getState());
        }
        return se;
    }

    /**
     * 添加店铺
     *
     * @param shop
     * @param thumbnail
     * @return
     */
    @Override
    @Transactional
    public ShopExecution addShop(Shop shop, ImageHolder thumbnail) {
        if (shop == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }
        try {
            // 给店铺信息赋初始值
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            int effectedNum = shopDao.insertShop(shop);
            if (effectedNum <= 0) {
                throw new ShopOperationException("店铺创建失败");
            } else {
                if (thumbnail.getImage() != null) {
                    // 存储图片
                    try {
                        addShopImg(shop, thumbnail);
                    } catch (Exception e) {
                        throw new ShopOperationException("addShopImg error:" + e.getMessage());
                    }
                    effectedNum = shopDao.updateShop(shop);
                    if (effectedNum <= 0) {
                        throw new ShopOperationException("更新图片地址失败");
                    }
                }
            }
        } catch (Exception e) {
            throw new ShopOperationException("addShop error:" + e.getMessage());
        }
        return new ShopExecution(ShopStateEnum.CHECK, shop);
    }

    /**
     * 根据shopId获取店铺
     *
     * @param shopId
     * @return
     */
    @Override
    public Shop getShopById(Long shopId) {
        return shopDao.getShopById(shopId);
    }

    /**
     * 更新店铺信息
     *
     * @param shop
     * @param thumbnail
     * @return
     */
    @Override
    public ShopExecution updateShop(Shop shop, ImageHolder thumbnail) {
        try {
            if (shop == null && thumbnail.getImage() == null) {
                return new ShopExecution(ShopStateEnum.NULL_SHOP);
            } else {
                // 1.先判断是否需要处理图片
                if (thumbnail.getImage() != null && thumbnail.getImageName() != null && !"".equals(thumbnail.getImageName())) {
                    Shop tempShop = shopDao.getShopById(shop.getShopId());
                    if (tempShop.getShopImg() != null) {
                        // 删除原有图片
                        ImageUtil.deleteFileOrPath(tempShop.getShopImg());
                    }
                    // 更新图片
                    addShopImg(shop, thumbnail);
                }
                // 2.更新店铺信息
                shop.setLastEditTime(new Date());
                int effectedNum = shopDao.updateShop(shop);
                if (effectedNum <= 0) {
                    return new ShopExecution(ShopStateEnum.INNER_ERROR);
                } else {
                    shop = shopDao.getShopById(shop.getShopId());
                    return new ShopExecution(ShopStateEnum.SUCCESS, shop);
                }
            }
        } catch (Exception e) {
            throw new ShopOperationException("updataShop error:" + e.getMessage());
        }
    }

    /**
     * 添加图片路径到数据库
     *
     * @param shop
     * @param thumbnail
     */
    private void addShopImg(Shop shop, ImageHolder thumbnail) {
        // 获取shop图片目录的相对路径
        String dest = PathUtil.getShopImagePath(shop.getShopId());
        String shopImgAddr = ImageUtil.generateThumbnail(thumbnail, dest);
        shop.setShopImg(shopImgAddr);
    }
}
