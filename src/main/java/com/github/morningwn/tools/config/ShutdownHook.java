package com.github.morningwn.tools.config;

import com.github.morningwn.tools.utils.WinUtils;
import com.github.morningwn.tools.utils.bean.BeanUtils;

/**
 * @author morningwn
 * @create in 2023/1/17 11:54
 */
public class ShutdownHook extends Thread {
    @Override
    public void run() {
        //
        WinUtils.setWinIconTop("Tool/Wallpaper");
        // 清除bean
        BeanUtils.destroy();
    }
}
