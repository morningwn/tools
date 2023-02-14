package com.github.morningwn.tools.entity;

/**
 * @author morningwn
 * @create in 2023/1/20 12:55
 */
public class WallpaperEntity {
    private String url;
    private WallpaperTypeEnum type;

    // todo 设置内容

    public WallpaperEntity() {
    }

    public WallpaperEntity(String url, WallpaperTypeEnum type) {
        this.url = url;
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public WallpaperTypeEnum getType() {
        return type;
    }

    public void setType(WallpaperTypeEnum type) {
        this.type = type;
    }
}
