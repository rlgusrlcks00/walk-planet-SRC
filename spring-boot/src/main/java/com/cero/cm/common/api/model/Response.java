package com.cero.cm.common.api.model;

import com.cero.cm.common.type.ResultCodeConst;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *  포인트 API 서버 연동 공통
 *  응답 규격
 */
@Data
public class Response<T> implements Serializable {

    /** 결과 코드 */
    @ApiModelProperty(example = "상태코드")
    private String resultCd = ResultCodeConst.SUCCESS.getCode();

    /** 결과 메시지 */
    @ApiModelProperty(example = "상태메시지")
    private String resultMsg;

    /** 결과 데이터 */
    @ApiModelProperty(example = "결과값")
    private T result;

    /**
     * API 응답 객체 기본 생성자
     */
    public Response(){}


    /**
     * API 응답 객체 기본 생성자
     * @param resultCd 결과 코드
     */
    public Response(String resultCd){
        this.resultCd = resultCd;
        this.result = null;
    }


    /**
     * API 응답 객체 기본 생성자
     * @param resultCd 결과 코드
     * @param resultMsg 결과 메사자
     */
    public Response(String resultCd, String resultMsg){
        this.resultCd = resultCd;
        this.resultMsg = resultMsg;
        this.result = null;
    }

    public Response(T result) {
        this.result = result;
    }


}
