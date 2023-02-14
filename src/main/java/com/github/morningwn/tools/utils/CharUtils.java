package com.github.morningwn.tools.utils;

/**
 * @author morningwn
 * @create in 2022/10/14 14:58
 */
public class CharUtils {
    public static boolean isBlankChar(char c) {
        return isBlankChar((int) c);
    }
    public static boolean isBlankChar(int c) {
        return Character.isWhitespace(c)
                || Character.isSpaceChar(c)
                || c == '\ufeff'
                || c == '\u202a'
                || c == '\u0000';
    }
}
