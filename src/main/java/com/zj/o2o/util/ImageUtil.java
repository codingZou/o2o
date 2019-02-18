package com.zj.o2o.util;

import com.zj.o2o.dto.ImageHolder;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * @author zj
 * @create 2019-01-18 18:49
 */
public class ImageUtil {
    private static final Random RANDOM = new Random();
    public static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();

    /**
     * 存放图片到指定路径
     *
     * @param thumbnail
     * @param targetAddr
     * @return
     */
    public static String generateThumbnail(ImageHolder thumbnail, String targetAddr) {
        String realFileName = getRandomFileName();
        String extension = getFileExtension(thumbnail.getImageName());
        makeDirPath(targetAddr);
        // 图片相对路径
        String relativeAddr = targetAddr + realFileName + extension;
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        try {
            Thumbnails.of(thumbnail.getImage()).size(200, 200)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/shuiyin.png")), 0.45f)
                    .outputQuality(0.8).toFile(dest);
        } catch (IOException e) {
            throw new RuntimeException("创建图片失败：" + e.toString());
        }
        return relativeAddr;
    }

    public static String generateNormalImg(ImageHolder thumbnail, String targetAddr) {
        String realFileName = getRandomFileName();
        String extension = getFileExtension(thumbnail.getImageName());
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realFileName + extension;
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        try {
            Thumbnails.of(thumbnail.getImage()).size(337, 640).outputQuality(0.5f).toFile(dest);
        } catch (IOException e) {
            throw new RuntimeException("创建缩略图失败：" + e.toString());
        }
        return relativeAddr;
    }

    /**
     * 判断目录是否存在，没有则创建
     *
     * @param targetAddr
     */
    private static void makeDirPath(String targetAddr) {
        String realFileParenPath = PathUtil.getImgBasePath() + targetAddr;
        File dirpath = new File(realFileParenPath);
        if (!dirpath.exists()) {
            dirpath.mkdirs();
        }
    }

    /**
     * 获取文件的扩展名
     *
     * @param fileName
     * @return
     */
    private static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 生成文件随机名
     *
     * @return
     */
    private static String getRandomFileName() {
        int ranNum = RANDOM.nextInt(89999) + 10000;
        String nowTimeStr = System.currentTimeMillis() + "";
        return nowTimeStr + ranNum;
    }

    public static void main(String[] args) throws IOException {
        Thumbnails.of(new File("C:/Users/hp/Desktop/picture/three.png"))
                .size(200, 200).watermark(Positions.BOTTOM_RIGHT,
                ImageIO.read(new File(basePath + "/shuiyin.png")), 0.5f).outputQuality(0.8f)
                .toFile("C:/Users/hp/Desktop/picture/newthree.png");
    }

    /**
     * 删除文件或目录
     *
     * @param storePath
     */
    public static void deleteFileOrPath(String storePath) {
        File fileOrPath = new File(PathUtil.getImgBasePath() + storePath);
        if (fileOrPath.exists()) {
            if (fileOrPath.isDirectory()) {
                // 删除该目录下的所有信息
                File files[] = fileOrPath.listFiles();
                for (int i = 0; i < files.length; i++) {
                    files[i].delete();
                }
            }
            fileOrPath.delete();
        }
    }
}
