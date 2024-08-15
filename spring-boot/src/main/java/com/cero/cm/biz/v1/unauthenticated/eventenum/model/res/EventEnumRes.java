package com.cero.cm.biz.v1.unauthenticated.eventenum.model.res;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EventEnumRes {
    private String eventType;
    private String eventValue;
}
