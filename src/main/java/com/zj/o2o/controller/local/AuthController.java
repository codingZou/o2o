package com.zj.o2o.controller.local;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zj
 * @create 2019-03-19 13:56
 */
@Controller
@RequestMapping("/local")
public class AuthController {
    @RequestMapping(value = "/userlogin")
    public String userlogin(HttpServletRequest request) {
        // 转发至登录页面
        return "local/userlogin";
    }

    @RequestMapping(value = "/userregister")
    public String userregister() {
        // 转发至注册页面
        return "local/register";
    }

    @RequestMapping(value = "/changepsw")
    public String changepwd() {
        // 转发至修改密码页面
        return "local/changepsw";
    }
}
