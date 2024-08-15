package com.cero.cm.exception;

public class CustomException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private com.cero.cm.exception.ErrorCode errorCode;

    public CustomException(com.cero.cm.exception.ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public CustomException(com.cero.cm.exception.ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public CustomException(String message) {
        super(message);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

}
