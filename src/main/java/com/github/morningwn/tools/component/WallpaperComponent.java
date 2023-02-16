package com.github.morningwn.tools.component;

import com.github.morningwn.tools.controller.WallpaperController;
import com.github.morningwn.tools.entity.*;
import com.github.morningwn.tools.mapper.ConfigMapper;
import com.github.morningwn.tools.utils.CollUtils;
import com.github.morningwn.tools.utils.StrUtils;
import com.github.morningwn.tools.utils.bean.BeanUtils;
import com.github.morningwn.tools.utils.bean.LifeBean;
import com.github.morningwn.tools.view.stage.ViewEnum;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author morningwn
 * @create in 2022/10/27 15:07
 */
public class WallpaperComponent implements LifeBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(WallpaperComponent.class);
    private int offset;
    private Timer timer;
    private WallpaperSetting setting;
    private ConfigMapper configMapper;
    private File wallpaperDir;

    public WallpaperComponent() {
        offset = 0;
    }

    @Override
    public void init() {
        configMapper = BeanUtils.getBean(ConfigMapper.class);
        ConfigEntity configEntity = configMapper.getByType(ConfigTypeEnum.WALLPAPER.getCode());
        if (Objects.nonNull(configEntity)) {
            setting = (WallpaperSetting) configEntity.getConfig();
        } else {
            setting = new WallpaperSetting();
        }
        if (StrUtils.isNotBlank(setting.getWallpaperDir())) {
            wallpaperDir = new File(setting.getWallpaperDir());
        }
    }

    @Override
    public void destroy() {
        if (timer != null) {
            timer.cancel();
        }
    }

    public void start() {
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> upWallpaper());
            }
        }, 0, TimeUnit.HOURS.toMillis(setting.getWallpaperUpdateFrequency()));
    }

    private void changeWallpaper(WallpaperEntity image) {
        WallpaperController controller = (WallpaperController) BeanUtils.getStage(ViewEnum.WALLPAPER).getController();
        controller.setWallpaper(image);
    }

    /**
     * 刷新
     */
    public void refreshWallpaper() {
        // 自定义优先
        if (wallpaperDir != null && wallpaperDir.isDirectory()) {
            List<WallpaperEntity> files = getLocalImage();

            if (CollUtils.isNotEmpty(files) && files.size() > offset) {
                WallpaperEntity image = files.get(offset);
                changeWallpaper(image);
                return;
            }
        }
        List<WallpaperEntity> images = BingComponent.getImageList();
        if (CollUtils.isNotEmpty(images) && images.size() > offset) {
            WallpaperEntity image = images.get(offset);
            changeWallpaper(image);
        }
    }


    /**
     * 下一张
     */
    public void upWallpaper() {
        // 自定义优先
        if (wallpaperDir != null && wallpaperDir.isDirectory()) {
            List<WallpaperEntity> files = getLocalImage();
            if (CollUtils.isNotEmpty(files)) {
                if (++offset >= files.size()) {
                    offset %= files.size();
                }
                WallpaperEntity image = files.get(offset);
                changeWallpaper(image);
                return;
            }
        }

        List<WallpaperEntity> images = BingComponent.getImageList();
        if (CollUtils.isEmpty(images)) {
            return;
        }
        if (++offset >= images.size()) {
            offset %= images.size();
        }
        WallpaperEntity image = images.get(offset);
        changeWallpaper(image);
    }

    /**
     * 上一张
     */
    public void downWallpaper() {
        // 自定义优先
        if (wallpaperDir != null && wallpaperDir.isDirectory()) {
            List<WallpaperEntity> files = getLocalImage();

            if (CollUtils.isNotEmpty(files)) {
                if (--offset < 0) {
                    offset = (files.size() + offset) % files.size();
                }
                WallpaperEntity image = files.get(offset);
                changeWallpaper(image);
                return;
            }
        }

        List<WallpaperEntity> images = BingComponent.getImageList();
        if (CollUtils.isEmpty(images)) {
            return;
        }
        if (--offset < 0) {
            offset = (images.size() + offset) % images.size();
        }
        WallpaperEntity image = images.get(offset);
        changeWallpaper(image);
    }

    private static final Set<String> WALLPAPER_TYPE = CollUtils.toSet("image/png", "image/jpeg", "image/jpg");

    private List<WallpaperEntity> getLocalImage() {
        LOGGER.info("getLocalImage");
        File[] files = wallpaperDir.listFiles();
        List<WallpaperEntity> fileList = new ArrayList<>();

        if (CollUtils.isEmpty(files)) {
            return fileList;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                continue;
            }
            try {
                String type = Files.probeContentType(file.toPath());
                if (WALLPAPER_TYPE.contains(type)) {
                    fileList.add(new WallpaperEntity(file.getPath(), WallpaperTypeEnum.Image));
                }
            } catch (IOException e) {
                LOGGER.error("can't get file type");
            }
        }

        return fileList;
    }
}
