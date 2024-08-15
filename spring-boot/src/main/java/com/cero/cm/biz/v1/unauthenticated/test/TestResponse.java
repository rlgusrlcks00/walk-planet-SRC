package com.cero.cm.biz.v1.unauthenticated.test;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TestResponse {
    private Long eventEnumId;
    private String eventType;
}
