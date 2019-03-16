package com.zj.o2o.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zj
 * @create 2019-01-26 13:45
 */
public class CodeUtil {
    public static boolean checkVerifyCode(HttpServletRequest request) {
        // 获取session中的验证码
        String verifyCodeExpected = (String) request.getSession().getAttribute(
                com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        String verifyCodeActual = request.getParameter("verifyCodeActual");
        // equalsIgnoreCase:忽悠大小写进行比较
        if (verifyCodeActual == null
                || !verifyCodeActual.equalsIgnoreCase(verifyCodeExpected)) {
            return false;
        }
        return true;
    }
}
