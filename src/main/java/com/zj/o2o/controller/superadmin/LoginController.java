package com.zj.o2o.controller.superadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zj
 * @create 2019-03-15 15:03
 */
@Controller
@RequestMapping(value = "/superadmin")
public class LoginController {
    @RequestMapping(value = "/login")
    public Map<String, Object> login() {
        Map<String, Object> map = new HashMap<>();

        return map;
    }
}
