package com.github.morningwn.tools;

import com.github.morningwn.tools.config.ShutdownHook;
import com.github.morningwn.tools.utils.bean.BeanUtils;
import javafx.application.Application;

/**
 * @author morningwn
 * @create in 2022/10/27 15:58
 */
public class Main {

    public static void main(String[] args) {
        BeanUtils.createBean();
        Runtime.getRuntime().addShutdownHook(new Thread(new ShutdownHook()));
        Application.launch(MainApplication.class);
    }
}
