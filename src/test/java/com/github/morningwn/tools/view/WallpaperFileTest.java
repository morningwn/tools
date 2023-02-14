package com.github.morningwn.tools.view;

import com.github.morningwn.tools.MainApplication;
import com.github.morningwn.tools.utils.bean.BeanUtils;
import com.github.morningwn.tools.view.stage.ViewEnum;
import javafx.application.Application;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author morningwn
 * @create in 2023/1/25 15:50
 */
public class WallpaperFileTest extends Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainApplication.class);

    @Override
    public void start(Stage stage) throws Exception {
        BeanUtils.setMainStage(stage);
        BeanUtils.getOrCreateStage(ViewEnum.WALLPAPER_FILE).show();
    }

    @Test
    public void test() {
        BeanUtils.createBean();
        Runtime.getRuntime().addShutdownHook(new Thread(BeanUtils::destroy));
        Application.launch(WallpaperFileTest.class);
        System.exit(0);
    }

}
