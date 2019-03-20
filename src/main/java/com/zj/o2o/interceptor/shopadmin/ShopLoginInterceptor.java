package com.zj.o2o.interceptor.shopadmin;

import com.zj.o2o.entity.PersonInfo;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zj
 * @create 2019-03-16 18:20
 */
public class ShopLoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从session中取出用户信息
        PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
        if (user != null) {
            //判断非空并且账号状态为1,用户类型为店家
            if (user != null && user.getUserId() != null && user.getUserId() > 0 && user.getEnableStatus() == 1
                    && user.getUserType() == 2) {
                return true;
            }
        }
        response.sendRedirect(request.getContextPath() + "/local/userlogin?usertype=2");
        return false;
    }
}
