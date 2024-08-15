package com.cero.cm.common.type;

public enum ResultCodeConst {
    //공통처리
    SUCCESS("0000"),            //성공하였습니다.
    NOT_FOUND_INFO("0010"),     //정보를 찾을 수 없습니다.

    //valid
    NOT_EXIST_PCODE("1001"),    //부모코드가 존재하지 않습니다.
    NOT_EXIST_DCODE("1002"),    //상세코드가 존재하지 않습니다.
    NOT_EXIST_ID("1003"),       //id가 존재하지 않습니다.
    NOT_FOUND_PCODE("1101"),    //부모코드 값이 존재하지 않습니다.
    NOT_FOUND_DCODE("1102"),    //상세코드 값이 존재하지 않습니다.
    NOT_VALID_PCODE("1201"),    //부모코드 값 형식이 잘못되었습니다.
    NOT_VALID_DCODE("1202"),    //상세코드 값 형식이 잘못되었습니다.
    NOT_VALID_USEYN("1203"),    //Use Type 값 형식이 잘못되었습니다.
    NOT_VALID_FORMAT("1204"),   //Format Type 값 형식이 잘못되었습니다.
    NOT_VALID_DATETIME("1205"),   //날짜 값 형식이 잘못되었습니다.

    NOT_VALID_VALUE("1300"),    //Value 형식이 잘못되었습니다.
    NOT_VALID_OVERLAP("1301"),    //중복된 데이터가 있습니다.
    NOT_VALID_TITLE("1302"),  //제목이 잘못되었습니다.
    NOT_VALID_CONTENT("1303"),  //내용이 잘못되었습니다.
    NOT_VALID_SCREEN_TP_CD("1304"),  //화면 코드가 잘못되었습니다.
    NOT_VALID_DATE_VALUE("1305"),  //날짜 등록 값이 잘못되었습니다.
    NOT_VALID_BANNER_TP_CD("1306"),  //배너 코드 값이 잘못되었습니다.
    NOT_VALID_EXPSR_TP_CD("1307"),  //배너 타입 코드 값이 잘못되었습니다.
    NOT_VALID_POPUP_TP_CD("1308"),  //팝업 타입 코드 값이 잘못되었습니다.
    NOT_VALID_POPUP_COOKIE_TP_CD("1309"),  //쿠키 저장 코드 값이 잘못되었습니다.
    NOT_VALID_GET_URL_LNKNG_TP_CD("1310"),  //URL 연결 코드 값이 잘못되었습니다.
    NOT_VALID_LEVEL("1311"),  //레벨이 잘못되었습니다.
    NOT_VALID_QUESTION("1312"),  //질문이 등록되지 않았습니다.
    IS_VALID_ANSWER("1313"),  //답변이 이미 등록되었습니다.
    NOT_VALID_CLAUS_TP_CD("1314"),  //약관 코드 값이 잘못되었습니다.
    NOT_VALID_EMAIL("1315"),  //Email 값이 잘못되었습니다.
    NOT_VALID_NAME("1316"),  //이름 값이 잘못되었습니다.
    NOT_VALID_PWD("1317"),  //패스워드 값이 잘못되었습니다.
    NOT_VALID_PMS("1318"),  //권한 값이 잘못되었습니다.
    NOT_VALID_IMAGE("1319"),  //이미지 값이 잘못되었습니다.
    IS_VALID_ANSWER_COMPLETE("1320"),  //답변을 변경하거나 추가할 수 없습니다.
    NOT_VALID_NOTICE_TP_CD("1321"),  //공지사항 값이 잘못되었습니다.
    NOT_VALID_GOODS_STATE_CD("1322"),  //상품상태코드 값이 잘못되었습니다.
    NOT_VALID_EXPIRY_TP_CD("1323"),  //유효기간코드 값이 잘못되었습니다.
    NOT_VALID_PURCHASE_TP_CD("1324"),  //구매방법코드 값이 잘못되었습니다.
    NOT_VALID_POINT("1325"),  //포인트 등록값 값이 잘못되었습니다.
    NOT_VALID_GOODS_CD("1326"),  //구매방법코드 값이 잘못되었습니다.
    NOT_VALID_AMOUNT("1327"),  //수량 등록값 값이 잘못되었습니다.




    NOT_VALID_CREATE("1901"),    //작성 권한이 없습니다.
    NOT_VALID_READ("1902"),    //조회 권한이 없습니다.
    NOT_VALID_UPDATE("1903"),    //수정 권한이 없습니다.
    NOT_VALID_DELETE("1904"),    //삭제 권한이 없습니다.
    USER_DISABLED("1905"),    //등록된 관리자가 아닙니다.
    INVALID_CREDENTIALS("1906"),    //로그인 아이디와 비밀번호를 입력하세요.


    //Fail
    FAIL("2000"),               //서비스 실행에 실패하였습니다.

    //System Error
    ERROR("9999");              //시스템에러가 발생하였습니다.
    private String code;

    ResultCodeConst(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
