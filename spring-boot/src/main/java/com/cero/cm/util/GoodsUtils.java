package com.cero.cm.util;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

/**
 * 상품 관련 공통 Utils.
 */
public class GoodsUtils {

    /**
     * 상품 고유 상품코드를 생성한다.
     *
     * @param firstCode 첫번째 코드 (한 자리만 )
     * @param secondCode 두번째 코드 (한 자리만 )
     * @param startIndex 생성된 코드의 시작 위치 ( 1 ~ 9 )
     * @param endIndex 생성된 코드의 종료 위치 ( 1 ~ 9 )
     * @return 상품 코드 ( 총 11 자리 )
     */
    public static String generateGoodsCode(
            @NotNull @Size(min = 1, max = 1) String firstCode,
            @NotNull @Size(min = 1, max = 1) String secondCode,
            @Size(min = 0, max = 9) int startIndex,
            @Size(min = 0, max = 9) int endIndex
    ) {
        // 현재 시간을 기반으로한 고유한 상품 코드 생성
        return firstCode + secondCode + "_" + UUID.randomUUID().toString().toUpperCase().substring(0, 8);
    }
}
