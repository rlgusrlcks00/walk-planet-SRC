package com.cero.cm.exception;


public class MemberException extends CustomException {
    private static final long serialVersionUID = -2116671122895194101L;
    public MemberException(ErrorCode e) {
        super(e);
    }
    public MemberException(ErrorCode e, String message) {
        super(e, message);
    }
}
