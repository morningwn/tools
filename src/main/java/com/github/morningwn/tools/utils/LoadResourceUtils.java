package com.github.morningwn.tools.utils;

import com.github.morningwn.tools.Main;

import java.net.URL;

/**
 * @author morningwn
 * @create in 2023/1/25 14:55
 */
public class LoadResourceUtils {
    public static URL loadView(String name) {
        return Main.class.getResource("view/" + name);
    }

    public static URL load(String name) {
        return Main.class.getResource(name);
    }
}
