package com.zj.o2o.interceptor.shopadmin;

import com.zj.o2o.entity.Shop;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author zj
 * @create 2019-03-19 13:10
 */
public class ShopPermissionInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        // 从session中获取当前用户可操作的店铺列表
        List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
        if (currentShop != null && shopList != null) {
            for (Shop shop : shopList) {
                // 判断当前操作的店铺是否在shopList中
                if (shop.getShopId() == currentShop.getShopId()) {
                    return true;
                }
            }
        }
        // 不存在表示没有该店铺的操作权限
        return false;
    }
}
