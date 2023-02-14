package com.github.morningwn.tools.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author morningwn
 * @create in 2022/10/21 14:31
 */
public class DateUtils {
    private static final String[] WEEK_NAME = {"", "一", "二", "三", "四", "五", "六", "日"};
    public static final String NORM_DATE_PATTERN = "yyyy-MM-dd";
    private static final DateTimeFormatter NORM_DATE_FORMAT = DateTimeFormatter.ofPattern(NORM_DATE_PATTERN);
    public static final String NORM_TIME_PATTERN = "HH:mm:ss";
    private static final DateTimeFormatter NORM_TIME_FORMAT = DateTimeFormatter.ofPattern(NORM_TIME_PATTERN);
    public static final String NORM_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter NORM_DATETIME_FORMAT = DateTimeFormatter.ofPattern(NORM_DATETIME_PATTERN);

    public static String getWeekName(LocalDateTime localDateTime) {
        return "星期" + WEEK_NAME[localDateTime.getDayOfWeek().getValue()];
    }

    public static String formatDate(LocalDateTime localDateTime) {
        return NORM_DATE_FORMAT.format(localDateTime);
    }

    public static String formatTime(LocalDateTime localDateTime) {
        return NORM_TIME_FORMAT.format(localDateTime);
    }

    public static String format(LocalDateTime localDateTime) {
        return NORM_DATETIME_FORMAT.format(localDateTime);
    }

    public static String format(LocalDateTime localDateTime, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return dateTimeFormatter.format(localDateTime);
    }

    public static Boolean pattern(String pattern) {
        try {
            DateTimeFormatter.ofPattern(pattern);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
