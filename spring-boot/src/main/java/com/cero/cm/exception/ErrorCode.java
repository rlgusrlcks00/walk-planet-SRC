package com.cero.cm.exception;

import com.cero.cm.util.MessageUtils;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;

public enum ErrorCode {

    // sample
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST.value(), null, "error.invalid.parameter"),


    COUPON_EXPIRATION(410, "C001", "error.coupon.expiration"),
    COUPON_NOT_FOUND(404, "C002", "error.coupon.not.found"),

    // 회원
    DUPLICATION_EMAIL(HttpStatus.OK.value(), "M101", "error.duplication.email"),
    DUPLICATION_NICKNAME(HttpStatus.OK.value(), "M102", "error.duplication.nickname"),
    NOT_FOUND_MEMBER(HttpStatus.OK.value(), "M103", "error.not.found.member"),
    WRONG_PASSWORD(HttpStatus.OK.value(), "M104", "error.wrong.password"),
    OTHER_LOGIN_TYPE(HttpStatus.OK.value(), "M105", "error.other.login.type"),
    DUPLICATION_MEMBERSHIP(HttpStatus.OK.value(), "M108", "error.duplication.membership"),
    NOT_FOUND_MEMBERSHIP(HttpStatus.OK.value(), "M109", "error.not.found.membership"),
    PROFILE_IMG_UPDATE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "M111", "error.profile.img.update.error"),
    NICKNAME_UPDATE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "M112", "error.nickname.update.error"),
    PWD_UPDATE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "M113", "error.pwd.update.error"),
    WITHDRAWAL_MEMBER(HttpStatus.OK.value(), "M114", "error.withdrawal.member"),
    FAIL_JOIN(HttpStatus.INTERNAL_SERVER_ERROR.value(), "M115", "error.fail.join"),
    NULL_LANG_CD(HttpStatus.OK.value(), "M116", "error.null.lang.cd"),
    NOT_MEMBERSHIP(HttpStatus.OK.value(), "M117", "error.not.membership"),
    DUPLICATION_EMAIL_30DAYS(HttpStatus.OK.value(), "M101", "error.duplication.email.30days"),
    //스토리
    STORY_SAVE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "S100", "error.story.save.error"),
    NOT_FOUND_STORY(HttpStatus.OK.value(), "S101", "error.not.found.story"),
    NOT_FOUND_MMBR_ID(HttpStatus.BAD_REQUEST.value(), "S102", "error.not.found.mmbr.id"),
    NOT_FOUND_STORY_ID(HttpStatus.BAD_REQUEST.value(), "S103", "error.not.found.story.id"),
    NO_SELECTED_STORY_LIST(HttpStatus.OK.value(), "S104", "error.no.selected.story.list"),

    NOT_FOUND_REPLY(HttpStatus.OK.value(), "S111", "error.not.found.reply"),
    NOT_REPORT_YOURSELF(HttpStatus.OK.value(), "S112", "error.not.report.yourself"),
    NOT_REPORT_ALREADY(HttpStatus.OK.value(), "S113", "error.not.report.already"),

    // 토큰
    TOKEN_FORBIDDEN(HttpStatus.FORBIDDEN.value(), null, "error.token.forbidden"),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED.value(), null, "error.token.expired"),

    // 구독
    FAIL_SUBSCRIBE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "F101", "error.fail.subscribe.error"),

    //인증
    EXPIRED_VERIFY_NUMBER(HttpStatus.OK.value(), "V101", "error.expired.verify.number"),
    INVALID_VERIFY_NUMBER(HttpStatus.OK.value(), "V102", "error.invalid.verify.number"),

    //이메일 전송
    FAIL_SEND_EMAIL(HttpStatus.INTERNAL_SERVER_ERROR.value(), "E101", "error.fail.send.email"),
    FAIL_SAVE_REDIS(HttpStatus.INTERNAL_SERVER_ERROR.value(), "E102", "error.fail.save.redis"),

    //액션, 챌린지
    NOT_FOUND_ACTION(HttpStatus.OK.value(), "A101", "error.not.found.action"),
    INVALID_TPC_TP_CD_TO_ACTION(HttpStatus.OK.value(), "A102", "error.invalid.tpc.tp.cd.to.action"),
    INVALID_TPC_TP_CD_TO_CHALLENGE(HttpStatus.OK.value(), "A103", "error.invalid.tpc.tp.cd.to.challenge"),
    ALREADY_ONGOING_ACTION(HttpStatus.OK.value(), "A104", "error.already.ongoing.action"),
    NOT_FOUND_CATE(HttpStatus.OK.value(), "A105", "error.not.found.cate"),
    NOT_FOUND_RECOM(HttpStatus.OK.value(), "A106", "error.not.found.recom"),
    RANGE_INVALID_PARAM(HttpStatus.OK.value(), "A107", "error.range.invalid.param"),
    NOT_FOUND_CHALLENGE(HttpStatus.OK.value(), "A108", "error.not.found.challenge"),
    NO_SELECTED_ACTION_LIST(HttpStatus.OK.value(), "A109", "error.no.selected.action.list"),
    NO_SELECTED_CHALLENGE_LIST(HttpStatus.OK.value(), "A110", "error.no.selected.challenge.list"),

    NOT_FOUND_INQUIRY(HttpStatus.OK.value(), "I101", "error.not.found.inquiry"),

    FAIL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "FE000", "error.fail.error"),

    FAIL_SAVE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "FE001", "error.fail.save.error"),

    SYSTEM_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "F999", "error.system.error"),

    //행동
    REQUIRE_META_VALUE(HttpStatus.OK.value(), "B001", "error.require.meta.value"),

    //common
    INVALID_LANG_TP_CD(HttpStatus.OK.value(), "C001", "error.invalid.lang.tp.cd"),
    CONTENTS_IS_NULL(HttpStatus.OK.value(), "C002", "error.contents.is.null"),
    INVALID_ID(HttpStatus.OK.value(), "C003", "error.invalid.id"),
    COMMON_EXCEPTION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "C999", "error.common.exception.error"),

    //포인트
    INVALID_POINT(HttpStatus.BAD_REQUEST.value(), "P001", "error.invalid.point"),
    NOT_ADMIN(HttpStatus.BAD_REQUEST.value(), "P002", "error.not.admin"),
    NOT_FOUND_POINT(HttpStatus.BAD_REQUEST.value(), "P003", "error.not.found.point"),
    NOT_FOUND_USER(HttpStatus.BAD_REQUEST.value(), "P004", "error.not.found.user"),
    NOT_FOUND_POINT_INFO(HttpStatus.BAD_REQUEST.value(), "P005", "error.not.found.point.info"),

    // 보상
    NOT_FOUND_REWARD(HttpStatus.OK.value(), "R101", "error.not.found.reward"),

    // STORE(상품) 관리 부분
    NOT_FOUND_GOODS_INFO(HttpStatus.OK.value(), "G000", "error.not.found.goods.info"),
    STORE_GOODS_VALID_VENDOR(HttpStatus.OK.value(), "G001", "error.goods.valid.vendor"),
    STORE_GOODS_VALID_CATEGORY(HttpStatus.OK.value(), "G002", "error.goods.valid.category"),
    STORE_GOODS_VALID_NAME(HttpStatus.OK.value(), "G003", "error.goods.valid.name"),
    STORE_GOODS_VALID_AMOUT(HttpStatus.OK.value(), "G004", "error.goods.valid.amount"),
    STORE_GOODS_VALID_POINT(HttpStatus.OK.value(), "G005", "error.goods.valid.point"),

    // STORE(상품) 거래 부분
    NOT_FOUND_GOODS_TRSTN_INFO(HttpStatus.OK.value(), "G010", "error.not.found.goods.trstn.info"),
    STORE_GOODS_TRSTN_VALID_MEMBER(HttpStatus.OK.value(), "G011", "error.goods.trstn.valid.member"),
    STORE_GOODS_TRSTN_VALID_GOODS(HttpStatus.OK.value(), "G012", "error.goods.trstn.valid.goods"),
    STORE_GOODS_TRSTN_VALID_CNT(HttpStatus.OK.value(), "G013", "error.goods.trstn.valid.cnt"),
    STORE_GOODS_TRSTN_VALID_POINT(HttpStatus.OK.value(), "G014", "error.goods.trstn.valid.point"),

    // STORE(상품) 배송정보 부분
    STORE_GOODS_DELVERYINFO_NOT_FOUND_INFO(HttpStatus.OK.value(), "G021", "error.goods.delveryinfo.not.found.info"),
    STORE_GOODS_DELVERYINFO_VALID_ORDRNM(HttpStatus.OK.value(), "G022", "error.goods.delveryinfo.valid.ordrnm"),
    STORE_GOODS_DELVERYINFO_VALID_TELNO(HttpStatus.OK.value(), "G023", "error.goods.delveryinfo.valid.telno"),
    STORE_GOODS_DELVERYINFO_VALID_ADDR(HttpStatus.OK.value(), "G024", "error.goods.delveryinfo.valid.addr"),
    ;

    private final String code;
    private final String message;
    private final int status;
    private static final MessageSourceAccessor messageSourceAccessor;

    static {
        messageSourceAccessor = MessageUtils.messageSourceAccessor;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return messageSourceAccessor.getMessage(message);
    }

    public int getStatus() {
        return status;
    }

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
}
