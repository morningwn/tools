package com.github.morningwn.tools.exception;

/**
 * @author morningwn
 * @create in 2023/02/16 22:17
 */
public class ToolsException extends RuntimeException {
    public ToolsException() {
    }

    public ToolsException(String message) {
        super(message);
    }

    public ToolsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ToolsException(Throwable cause) {
        super(cause);
    }

    public ToolsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
