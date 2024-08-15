package com.cero.cm.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.EnumSet;

/**
 * {@link LegacyCommonType} enum을 String 과 상호 변환하는 유틸리티 클래스
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LegacyEnumValueConvertUtil {

	public static <T extends Enum<T> & LegacyCommonType> T ofLegacyCode(Class<T> enumClass,
																		String legacyCode) {
		if(StringUtils.isBlank(legacyCode)) {
			return null;
		}

		return EnumSet.allOf(enumClass).stream()
				.filter(v -> v.getLegacyCode().equals(legacyCode))
				.findAny()
				.orElseThrow(() -> new IllegalArgumentException(String.format("enum= [%s], legacyCode=[%s]가 존재하지 않습니다.",enumClass.getName(), legacyCode)));
	}


	public static <T extends Enum<T> & LegacyCommonType> String toLegacyCode(T enumValue) {
		if (enumValue == null) {
			return "";
		}
		return enumValue.getLegacyCode();
	}

}
