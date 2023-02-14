package com.github.morningwn.tools.utils;

import com.google.gson.Gson;

/**
 * @author morningwn
 * @create in 2022/10/21 14:26
 */
public class JSONUtils {
    private static final Gson GSON = new Gson();

    public static String toJson(Object o) {
        return GSON.toJson(o);
    }

    public static <T> T toBean(String json, Class<T> tClass) {
        return GSON.fromJson(json, tClass);
    }
}
