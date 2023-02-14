package com.github.morningwn.tools.entity;

/**
 * @author morningwn
 * @create in 2022/11/7 19:25
 */
public enum ConfigTypeEnum {
    NON(String.class, -1, "未知"),
    FILE(FileListSetting.class, 1, "文件配置"),
    WALLPAPER(WallpaperSetting.class, 2, "壁纸设置");
    private Class<?> type;
    private Integer code;
    private String desc;

    ConfigTypeEnum(Class<?> type, Integer code, String desc) {
        this.type = type;
        this.code = code;
        this.desc = desc;
    }

    public Class<?> getType() {
        return type;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static ConfigTypeEnum getByCode(Integer code) {
        for (ConfigTypeEnum value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return NON;
    }

    public static Class<?> getClassByCode(Integer code) {
        return getByCode(code).getType();
    }
}
