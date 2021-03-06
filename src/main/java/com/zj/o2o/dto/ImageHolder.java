package com.zj.o2o.dto;

import java.io.InputStream;

/**
 * @author zj
 * @create 2019-02-16 16:55
 */
public class ImageHolder {
    private String imageName;
    private InputStream image;

    public ImageHolder() {
    }

    public ImageHolder(String imageName, InputStream image) {
        this.imageName = imageName;
        this.image = image;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public InputStream getImage() {
        return image;
    }

    public void setImage(InputStream image) {
        this.image = image;
    }
}
