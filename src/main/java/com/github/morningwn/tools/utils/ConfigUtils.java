package com.github.morningwn.tools.utils;

import java.io.File;

/**
 * @author morningwn
 * @create in 2022/11/3 19:35
 */
public class ConfigUtils {

    static {
        File tmp = new File(getTmpDir());
        if (!tmp.exists()) {
            tmp.mkdirs();
        }
        File data = new File(getDataDir());
        if (!data.exists()) {
            data.mkdirs();
        }
    }

    public static String getBaseDir() {
        return System.getProperty("user.home") + File.separator + "AppData" + File.separator + "Local" + File.separator + "Tools";
    }

    public static String getTmpDir() {
        return getBaseDir() + File.separator + "tmp" + File.separator;
    }

    public static String getDataDir() {
        return getBaseDir() + File.separator + "data" + File.separator;
    }

    public static String getDefaultFileDir() {
        return System.getProperty("user.home") + File.separator + "Desktop";
    }
}
