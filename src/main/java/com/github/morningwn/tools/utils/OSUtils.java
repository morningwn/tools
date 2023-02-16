package com.github.morningwn.tools.utils;

/**
 * 判断系统类型
 *
 * @author morningwn
 * @create in 2023/02/16 22:08
 */
public class OSUtils {
    private static final String OS_TYPE = System.getProperty("os.name").toUpperCase();

    public static String getOsType() {
        return OS_TYPE;
    }

    public static boolean isWindows() {
        return OS_TYPE.contains("WINDOWS");
    }

    public static boolean isMac() {
        return OS_TYPE.contains("MAC");
    }

    public static boolean isLinux() {
        return OS_TYPE.contains("LINUX");
    }

}
