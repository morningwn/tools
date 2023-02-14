package com.github.morningwn.tools.entity;

import com.github.morningwn.tools.utils.DateUtils;
import com.github.morningwn.tools.utils.StrUtils;

import java.util.Objects;

/**
 * @author morningwn
 * @create in 2022/11/19 12:46
 */
public class WallpaperSetting {
    public static final String DATE_PATTERN_DEFAULT = DateUtils.NORM_DATETIME_PATTERN;

    private String datePattern;
    private String wallpaperDir;
    /**
     * 壁纸轮换时间，单位小时
     */
    public static final int WALLPAPER_UPDATE_FREQUENCY_DEFAULT = 4;
    private Integer wallpaperUpdateFrequency;
    /**
     *
     */
    public static final boolean SHOWTIME_DEFAULT = Boolean.TRUE;
    private Boolean showTime;
    /**
     * 当前壁纸json
     */
    private String curWallpaper;

    public WallpaperSetting() {
    }

    public String getDatePattern() {
        if (StrUtils.isNotBlank(datePattern)) {
            return datePattern;
        } else {
            return DATE_PATTERN_DEFAULT;
        }
    }

    public void setDatePattern(String datePattern) {
        this.datePattern = datePattern;
    }

    public String getWallpaperDir() {
        return wallpaperDir;
    }

    public void setWallpaperDir(String wallpaperDir) {
        this.wallpaperDir = wallpaperDir;
    }

    public Integer getWallpaperUpdateFrequency() {
        if (Objects.nonNull(wallpaperUpdateFrequency)) {
            return wallpaperUpdateFrequency;
        } else {
            return WALLPAPER_UPDATE_FREQUENCY_DEFAULT;
        }
    }

    public void setWallpaperUpdateFrequency(Integer wallpaperUpdateFrequency) {
        this.wallpaperUpdateFrequency = wallpaperUpdateFrequency;
    }

    public Boolean getShowTime() {
        if (Objects.nonNull(showTime)) {
            return showTime;
        } else {
            return SHOWTIME_DEFAULT;
        }
    }

    public void setShowTime(Boolean showTime) {
        this.showTime = showTime;
    }

    public String getCurWallpaper() {
        return curWallpaper;
    }

    public void setCurWallpaper(String curWallpaper) {
        this.curWallpaper = curWallpaper;
    }
}
