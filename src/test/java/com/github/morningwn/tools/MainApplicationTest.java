package com.github.morningwn.tools;

import com.github.morningwn.tools.utils.bean.BeanUtils;
import com.github.morningwn.tools.view.stage.ViewEnum;
import javafx.application.Application;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author morningwn
 * @create in 2023/1/17 11:07
 */
public class MainApplicationTest extends Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainApplication.class);

    @Override
    public void start(Stage stage) throws Exception {
        BeanUtils.setMainStage(stage);
        BeanUtils.getOrCreateStage(ViewEnum.SETTING).show();
    }
}
