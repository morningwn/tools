package com.github.morningwn.tools.utils;

import java.util.*;

/**
 * @author morningwn
 * @create in 2022/10/27 15:14
 */
public class CollUtils {

    public static boolean isEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }

    public static boolean isNotEmpty(List<?> list) {
        return !isEmpty(list);
    }

    public static <T> List<T> toList(T[] arr) {
        if (Objects.nonNull(arr)) {
            return Arrays.asList(arr);
        } else {
            return new ArrayList<>();
        }
    }

    @SafeVarargs
    public static <T> Set<T> toSet(T... ts) {
        Set<T> set = new HashSet<>();
        if (isNotEmpty(ts)) {
            set.addAll(Arrays.asList(ts));
        }
        return set;
    }

    public static <T> boolean isEmpty(T[] arr) {
        return arr == null || arr.length == 0;
    }

    public static <T> boolean isNotEmpty(T[] arr) {
        return !isEmpty(arr);
    }

}
