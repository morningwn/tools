package com.github.morningwn.tools;

import com.github.morningwn.tools.entity.ConfigEntity;
import com.github.morningwn.tools.entity.ConfigTypeEnum;
import com.github.morningwn.tools.entity.WallpaperSetting;
import com.github.morningwn.tools.mapper.ConfigMapper;
import com.github.morningwn.tools.utils.bean.BeanUtils;
import com.github.morningwn.tools.view.ToolsSystemTray;
import com.github.morningwn.tools.view.stage.ViewEnum;
import javafx.application.Application;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * @author morningwn
 * @create in 2022/9/7 10:49
 */
public class MainApplication extends Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainApplication.class);
    private WallpaperSetting setting;
    private ConfigMapper configMapper;

    @Override
    public void start(Stage stage) throws Exception {
        BeanUtils.setMainStage(stage);
        configMapper = BeanUtils.getBean(ConfigMapper.class);
        ConfigEntity configEntity = configMapper.getByType(ConfigTypeEnum.WALLPAPER.getCode());
        if (Objects.nonNull(configEntity)) {
            setting = (WallpaperSetting) configEntity.getConfig();
        } else {
            setting = new WallpaperSetting();
        }
        if (setting.getShowTime()) {
            BeanUtils.getOrCreateStage(ViewEnum.TIME).show();
        }
        BeanUtils.getOrCreateStage(ViewEnum.WALLPAPER).show();
        new ToolsSystemTray().setSystemTray();
    }
}
