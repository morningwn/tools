package com.github.morningwn.tools.controller;

import com.github.morningwn.tools.MainApplicationTest;
import com.github.morningwn.tools.utils.bean.BeanUtils;
import javafx.application.Application;
import org.junit.jupiter.api.Test;

/**
 * @author morningwn
 * @create in 2023/1/25 15:49
 */
class WallpaperFileControllerTest {
    @Test
    void main() {
        BeanUtils.createBean();
        Runtime.getRuntime().addShutdownHook(new Thread(BeanUtils::destroy));
        Application.launch(MainApplicationTest.class);
    }
}