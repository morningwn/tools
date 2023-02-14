package com.github.morningwn.tools;

import com.github.morningwn.tools.utils.bean.BeanUtils;
import javafx.application.Application;
import org.junit.jupiter.api.Test;

/**
 * @author morningwn
 * @create in 2023/1/17 11:06
 */
class MainTest {
    @Test
    void main() {
        BeanUtils.createBean();
        Runtime.getRuntime().addShutdownHook(new Thread(BeanUtils::destroy));
        Application.launch(MainApplicationTest.class);
        System.exit(0);
    }
}