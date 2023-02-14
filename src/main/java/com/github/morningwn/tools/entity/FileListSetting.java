package com.github.morningwn.tools.entity;

import com.github.morningwn.tools.utils.StrUtils;

/**
 * @author morningwn
 * @create in 2022/11/7 19:26
 */
public class FileListSetting {
    /**
     * 文件夹映射路径
     */
    private String fileDir;

    public FileListSetting() {
    }

    public String getFileDir() {
        if (StrUtils.isBlank(fileDir)) {
            return "D:\\新建文件夹";
        } else {
            return fileDir;
        }
    }

    public void setFileDir(String fileDir) {
        this.fileDir = fileDir;
    }
}
