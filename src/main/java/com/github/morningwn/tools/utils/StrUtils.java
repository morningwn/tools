package com.github.morningwn.tools.utils;

/**
 * @author morningwn
 * @create in 2022/10/14 14:35
 */
public class StrUtils {
    public static final String EMPTY = "";

    public static boolean isNotBlank(CharSequence str) {
        return false == isBlank(str);
    }

    public static boolean isBlank(CharSequence str) {
        final int length;
        if ((str == null) || ((length = str.length()) == 0)) {
            return true;
        }

        for (int i = 0; i < length; i++) {
            // 只要有一个非空字符即为非空字符串
            if (!CharUtils.isBlankChar(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }
}
