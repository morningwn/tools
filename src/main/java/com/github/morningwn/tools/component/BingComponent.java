package com.github.morningwn.tools.component;

import com.github.morningwn.tools.entity.BingImageEntity;
import com.github.morningwn.tools.entity.BingImageItemEntity;
import com.github.morningwn.tools.entity.WallpaperEntity;
import com.github.morningwn.tools.entity.WallpaperTypeEnum;
import com.github.morningwn.tools.utils.ConfigUtils;
import com.github.morningwn.tools.utils.HttpUtils;
import com.github.morningwn.tools.utils.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author morningwn
 * @create in 2022/10/21 15:20
 */
public class BingComponent {
    private static final Logger LOGGER = LoggerFactory.getLogger(BingComponent.class);
    private static final String BASE_URL = "https://cn.bing.com/";
    /**
     * format结果格式
     * idx不存在或者等于0时，输出当天的图片，-1为已经预备用于明天显示的信息，1则为昨天的图片，以此类推，idx最多获取到前16天的图片信息
     * n，必要参数。这是输出信息的数量至多输出8条
     */
    private static final String INFO = "https://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=8";

    @Deprecated
    public static List<BingImageItemEntity> getImages() {
        String resp = HttpUtils.get(INFO);
        BingImageEntity bingImageEntity = JSONUtils.toBean(resp, BingImageEntity.class);
        return bingImageEntity.getImages();
    }

    public static List<WallpaperEntity> getImageList() {
        String resp = HttpUtils.get(INFO);
        LOGGER.info("getImageList resp: {}", resp);
        BingImageEntity bingImageEntity = JSONUtils.toBean(resp, BingImageEntity.class);
        List<WallpaperEntity> wallpaperEntities = new ArrayList<>();
        for (BingImageItemEntity image : bingImageEntity.getImages()) {
            WallpaperEntity wallpaperEntity = new WallpaperEntity();
            wallpaperEntity.setUrl(BASE_URL + image.getUrl());
            wallpaperEntity.setType(WallpaperTypeEnum.Image);
            wallpaperEntities.add(wallpaperEntity);
        }
        return wallpaperEntities;
    }

    @Deprecated
    public static File getImage(BingImageItemEntity bingImage) {
        File file = new File(ConfigUtils.getTmpDir() + bingImage.getHsh());
        if (file.exists()) {
            return file;
        }
        byte[] bytes = HttpUtils.getByte(BASE_URL + bingImage.getUrl());
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(bytes);
            fos.flush();
        } catch (IOException e) {
            LOGGER.error("BingComponent#getImage fail", e);
        }
        return file;
    }

}
