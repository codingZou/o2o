package com.zj.o2o.interceptor.frontend;

import com.zj.o2o.entity.PersonInfo;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author zj
 * @create 2019-03-19 17:11
 */
public class FrontendLoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从session中取出用户信息
        PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
        if (user != null) {
            //判断非空并且账号状态为1,用户类型为客户
            if (user != null && user.getUserId() != null && user.getUserId() > 0 && user.getEnableStatus() == 1
                    && user.getUserType() == 1) {
                return true;
            }
        }
        PrintWriter pw = response.getWriter();

        response.sendRedirect(request.getContextPath() + "/local/userlogin?usertype=1");
        return false;
    }
}
