package com.zj.o2o.util;

/**
 * @author zj
 * @create 2019-01-18 20:23
 */
public class PathUtil {
    private static String seperator = System.getProperty("file.separator");

    public static String getImgBasePath() {
        String os = System.getProperty("os.name");// 获取操作系统
        String basePath = "";
        if (os.toLowerCase().startsWith("win")) {
            basePath = "F:/projectdev/image";
        } else {
            basePath = "/usr/local/projectdev/image";
        }
        // 因不同操作系统分隔符不一样所以统一替换
        basePath = basePath.replace("/", seperator);
        return basePath;
    }

    public static String getShopImagePath(long shopId) {
        String imagePath = "/upload/item/shop/" + shopId + "/";
        return imagePath.replace("/", seperator);
    }
}
