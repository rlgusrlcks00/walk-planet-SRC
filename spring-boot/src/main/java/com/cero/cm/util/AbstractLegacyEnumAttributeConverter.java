package com.cero.cm.util;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;

@Getter
public class AbstractLegacyEnumAttributeConverter<E extends  Enum<E> & LegacyCommonType> implements AttributeConverter<E, String> {

	private Class<E> targetEnumClass;
	private boolean nullable;
	private String enumName;

	public AbstractLegacyEnumAttributeConverter(Class<E> targetEnumClass, boolean nullable, String enumName) {
		this.targetEnumClass = targetEnumClass;
		this.nullable = nullable;
		this.enumName = enumName;
	}


	@Override
	public String convertToDatabaseColumn(E attribute) {
		if (!nullable && attribute == null) {
			throw new IllegalArgumentException(String.format("%s는 NULL로 저장할 수 없습니다.", enumName));
		}
		return LegacyEnumValueConvertUtil.toLegacyCode(attribute);
	}

	@Override
	public E convertToEntityAttribute(String dbData) {
		if (!nullable && StringUtils.isBlank(dbData)) {
			throw new IllegalArgumentException(String.format("%s는 DB에 NULL혹은 Empty(%s)로 저장되어 있습니다.", enumName, dbData));
		}
		return LegacyEnumValueConvertUtil.ofLegacyCode(targetEnumClass, dbData);
	}
}
