package com.zj.o2o.controller.local;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zj.o2o.dto.ImageHolder;
import com.zj.o2o.dto.LocalAuthExecution;
import com.zj.o2o.entity.LocalAuth;
import com.zj.o2o.entity.PersonInfo;
import com.zj.o2o.enums.LocalAuthStateEnum;
import com.zj.o2o.service.LocalAuthService;
import com.zj.o2o.util.CodeUtil;
import com.zj.o2o.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zj
 * @create 2019-03-15 18:39
 */
@Controller
@RequestMapping(value = "/local")
public class UserAuthController {
    @Autowired
    private LocalAuthService localAuthService;

    /**
     * 用户登录
     *
     * @param request
     * @param userName
     * @param password
     * @param verifyCodeActual
     * @param needVerify
     * @return
     */
    @RequestMapping(value = "/userlogincheck", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> userLoginCheck(HttpServletRequest request, String userName, String password,
                                               String verifyCodeActual, boolean needVerify, String usertype) {
        Map<String, Object> modelMap = new HashMap<>();
        if (needVerify && !CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "错误验证码");
            return modelMap;
        }
        if (userName != null && !"".equals(userName) && password != null && !"".equals(password)) {
            password = MD5.getMd5(password);
            LocalAuth localAuth = localAuthService.getLocalAuthByUserNameAndPwd(userName, password);
            // 判空并且判断账户类型是否一致
            if (localAuth != null && localAuth.getPersonInfo() != null &&
                    String.valueOf(localAuth.getPersonInfo().getUserType()).equals(usertype)) {
                // 将用户信息存入session中
                request.getSession().setAttribute("user", localAuth.getPersonInfo());
                modelMap.put("success", true);
                modelMap.put("user", localAuth.getPersonInfo());
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "用户名或密码错误");
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "用户名或密码不能为空");
        }
        return modelMap;
    }

    /**
     * 注册用户
     *
     * @param request
     * @param thumbnail
     * @return
     */
    @RequestMapping(value = "/userregister", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> userRegister(HttpServletRequest request, @RequestParam(value = "thumbnail")
            CommonsMultipartFile thumbnail) {
        Map<String, Object> modelMap = new HashMap<>();
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码错误!");
            return modelMap;
        }
        ObjectMapper mapper = new ObjectMapper();
        LocalAuth localAuth = null;
        String localAuthStr = request.getParameter("localAuthStr");
        ImageHolder imageHolder = null;
        if (thumbnail == null) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "上传图片不能为空");
            return modelMap;
        }
        try {
            localAuth = mapper.readValue(localAuthStr, LocalAuth.class);
            imageHolder = new ImageHolder(thumbnail.getOriginalFilename(), thumbnail.getInputStream());
            if (localAuth != null && localAuth.getUserName() != null && localAuth.getPassword() != null) {
                LocalAuthExecution le = localAuthService.register(localAuth, imageHolder);
                if (le.getState() == LocalAuthStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", le.getStateInfo());
                }
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "用户名或密码不能为空");
                return modelMap;
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 修改密码
     *
     * @param localAuth
     * @param newPassword
     * @return
     */
    @RequestMapping(value = "/updatelocalauthpwd", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateLocalAuthPwd(HttpServletRequest request, LocalAuth localAuth, String newPassword) {
        Map<String, Object> modelMap = new HashMap<>();
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码错误!");
            return modelMap;
        }
        LocalAuthExecution le = null;
        // 获取session中的用户信息
        PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
        if (user != null && user.getUserId() != null) {
            try {
                localAuth.setUserId(user.getUserId());
                le = localAuthService.modifyLocalAuth(localAuth, newPassword);
                if (le.getState() == LocalAuthStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", le.getStateInfo());
                }
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "seesion is null");
        }
        return modelMap;
    }

    /**
     * 退出系统
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> userLogoutCheck(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap();
        // 清除session中的数据
        request.getSession().setAttribute("user", null);
        modelMap.put("success", true);
        return modelMap;

    }
}
