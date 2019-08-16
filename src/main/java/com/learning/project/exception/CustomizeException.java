package com.learning.project.exception;

/**
 * @author Youngz
 * @date 2019/8/15 - 17:48
 */
public class CustomizeException extends RuntimeException {
    private String message;
    private Integer code;

    @Override
    public String getMessage() {
        return message;
    }


    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.message = errorCode.getMessage();
        this.code = errorCode.getCode();
    }

    public Integer getCode() {
        return code;
    }
}
