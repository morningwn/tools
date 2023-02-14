package com.github.morningwn.tools.component;

import com.github.morningwn.tools.entity.BingImageItemEntity;
import com.github.morningwn.tools.utils.JSONUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

/**
 * @author morningwn
 * @create in 2022/10/27 11:54
 */
class BingComponentTest {

    @Test
    void getImages() {
        List<BingImageItemEntity> images = BingComponent.getImages();
        System.out.println(JSONUtils.toJson(images));
    }

    @Test
    void getImage() {
        List<BingImageItemEntity> images = BingComponent.getImages();
        File image = BingComponent.getImage(images.get(1));
//        WinUtils.setWallpaper(image);
    }
}