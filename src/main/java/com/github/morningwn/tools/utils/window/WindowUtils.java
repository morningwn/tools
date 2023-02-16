package com.github.morningwn.tools.utils.window;

import com.github.morningwn.tools.exception.NotSupportException;
import com.github.morningwn.tools.utils.OSUtils;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.File;

/**
 * @author morningwn
 * @create in 2022/10/19 15:55
 */
public class WindowUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(WindowUtils.class);

    public static double getHeight() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension scrnsize = toolkit.getScreenSize();
        return scrnsize.getHeight();
    }

    public static double getWidth() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension scrnsize = toolkit.getScreenSize();
        return scrnsize.getWidth();
    }

    /**
     * 将窗口置于windows系统桌面与图表之间
     *
     * @param title
     */
    public static void setWinIconAfter(String title) {
        if (OSUtils.isWindows()) {
            Win64.setWinIconAfter(title);
        } else if (OSUtils.isLinux()) {
            Linux.setWinIconAfter(title);
        } else {
            throw new NotSupportException();
        }
    }

    /**
     * 将窗口移动到桌面图标上层
     */
    public static void setWinIconTop(String title) {
        if (OSUtils.isWindows()) {
            Win64.setWinIconTop(title);
        } else if (OSUtils.isLinux()) {
            Linux.setWinIconTop(title);
        } else {
            throw new NotSupportException();
        }
    }
}
