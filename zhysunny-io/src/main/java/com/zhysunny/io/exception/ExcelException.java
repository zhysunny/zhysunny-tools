package com.zhysunny.io.exception;

/**
 * @author zhysunny
 * @date 2021/6/19 11:10
 */
public class ExcelException extends RuntimeException {
    public ExcelException() {
    }

    public ExcelException(String message) {
        super(message);
    }

    public ExcelException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcelException(Throwable cause) {
        super(cause);
    }
}
