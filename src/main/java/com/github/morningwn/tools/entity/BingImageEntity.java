package com.github.morningwn.tools.entity;

import java.util.List;

/**
 * @author morningwn
 * @create in 2022/10/20 10:21
 */
public class BingImageEntity {
    private List<BingImageItemEntity> images;

    public BingImageEntity() {
    }

    public BingImageEntity(List<BingImageItemEntity> images) {
        this.images = images;
    }

    public List<BingImageItemEntity> getImages() {
        return images;
    }

    public void setImages(List<BingImageItemEntity> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "BingImageEntity{" +
                "images=" + images +
                '}';
    }
}
