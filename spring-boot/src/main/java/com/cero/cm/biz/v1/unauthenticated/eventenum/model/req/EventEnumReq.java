package com.cero.cm.biz.v1.unauthenticated.eventenum.model.req;

import com.cero.cm.db.entity.EventEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EventEnumReq {
    private String eventType;

    public EventEnum toEntity() {
        return EventEnum.builder()
                .eventType(eventType)
                .build();
    }
}
