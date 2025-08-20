package org.example.newsfeed.common.exception;

public class MyCustomException extends RuntimeException {
    private final ErrorCode errorCode;

    public MyCustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public String getCode() {
        return errorCode.getCode();
    }
}
