package com.cero.cm.exception;

public class CommonCodeException extends CustomException {

    private static final long serialVersionUID = -2116671122895194101L;

    public CommonCodeException(ErrorCode e) {
        super(e);
    }

    public CommonCodeException(String errorMessage) {
        super(errorMessage);
    }

}
