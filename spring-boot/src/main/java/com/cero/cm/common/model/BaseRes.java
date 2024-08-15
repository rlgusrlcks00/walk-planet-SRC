package com.cero.cm.common.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 공통 반환 Res DTO.
 */
@Getter
@AllArgsConstructor
public class BaseRes {
    @Schema(description = "등록자 ID")
    private Long regId;

    @Schema(description = "등록일시")
    private LocalDateTime regDt;

    @Schema(description = "수정자 ID")
    private Long modId;

    @Schema(description = "수정일시")
    private LocalDateTime modDt;

    /* 기본 생성자. */
    public BaseRes() {};

    public BaseRes(Long regId, LocalDateTime regDt) {
        this.regId = regId;
        this.regDt = regDt;
    };

    public String getRegDtYmd() {
        if(regDt == null) {
            return "";
        }
        return regDt.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    }

    public String getRegDtYmdHms() {
        if(regDt == null) {
            return "";
        }
        return regDt.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    }

    public String getModDtYmd() {
        if(modDt == null) {
            return "";
        }
        return modDt.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    }

    public String getModDtYmdHms() {
        if(modDt == null) {
            return "";
        }
        return modDt.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    }

}
