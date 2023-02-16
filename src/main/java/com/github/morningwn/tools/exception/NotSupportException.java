package com.github.morningwn.tools.exception;

import com.github.morningwn.tools.utils.OSUtils;

/**
 * @author morningwn
 * @create in 2023/02/16 22:18
 */
public class NotSupportException extends ToolsException {
    public NotSupportException() {
        this("系统：" + OSUtils.getOsType() + "不支持");
    }

    public NotSupportException(String message) {
        super(message);
    }
}
