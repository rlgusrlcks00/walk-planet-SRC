package com.cero.cm.exception;

import com.cero.cm.common.type.ResultCodeConst;
import com.cero.cm.util.MessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    /**
     * 1. 에러코드 정리 enum 클래스로 작성
     * 2. Exception 발생시 응답하는 에러 정보 클래스 작성
     * 3. 사용자 정의 Exception 클래스 작성
     * 4. Exception 발생시 전역으로 처리할 exception handler 작성
     * 5. 사용자등록관련 클래스작성 서비스에서 중복 exception 발생
     * 6. api 실행 및 exception 결과 확인
     */

    private final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<com.cero.cm.exception.ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        logger.error("handleHttpRequestMethodNotSupportedException", e);

        final com.cero.cm.exception.ErrorResponse response
                = com.cero.cm.exception.ErrorResponse
                .create()
                .status(HttpStatus.METHOD_NOT_ALLOWED.value())
                .message(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

    //@Valid 검증 실패 시 Catch
    @ExceptionHandler(InvalidParameterException.class)
    protected ResponseEntity<com.cero.cm.exception.ErrorResponse> handleInvalidParameterException(InvalidParameterException e) {
        logger.error("handleInvalidParameterException", e);

        ErrorCode errorCode = e.getErrorCode();

        com.cero.cm.exception.ErrorResponse response
                = com.cero.cm.exception.ErrorResponse
                .create()
                .status(errorCode.getStatus())
                .message(e.toString())
                .errors(e.getErrors());

        return new ResponseEntity<>(response, HttpStatus.resolve(errorCode.getStatus()));
    }

    //CustomException을 상속받은 클래스가 예외를 발생 시킬 시, Catch하여 ErrorResponse를 반환한다.
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<com.cero.cm.exception.ErrorResponse> handleCustomException(CustomException e) {
        logger.error("handleAllException", e);

        ErrorCode errorCode = e.getErrorCode();
        com.cero.cm.exception.ErrorResponse response = null;
        if (errorCode != null) {
            response
                    = com.cero.cm.exception.ErrorResponse
                    .create()
                    .status(errorCode.getStatus())
                    .code(errorCode.getCode())
                    .message(errorCode.getMessage());
        } else {
            response
                    = com.cero.cm.exception.ErrorResponse
                    .create()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .code(ErrorCode.COMMON_EXCEPTION_ERROR.getCode())
                    .message(e.getMessage());
        }


        return new ResponseEntity<>(response, HttpStatus.resolve(response.getStatus()));
    }

    //모든 예외를 핸들링하여 ErrorResponse 형식으로 반환한다.
    @ExceptionHandler(CommonCodeException.class)
    protected ResponseEntity<com.cero.cm.exception.ErrorResponse> handleCommomCodeException(Exception e) {
        logger.error("CommonCodeException", e);

        com.cero.cm.exception.ErrorResponse response
                = com.cero.cm.exception.ErrorResponse
                .create()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(e.toString());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //모든 예외를 핸들링하여 ErrorResponse 형식으로 반환한다.
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<com.cero.cm.exception.ErrorResponse> handleException(Exception e) {
        logger.error("handleException", e);

        com.cero.cm.exception.ErrorResponse response
                = ErrorResponse
                .create()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(MessageUtils.getMessage(ResultCodeConst.ERROR.getCode())); // 시스템 에러로 통일

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
