package com.cero.cm.biz.v1.authenticated.point.model.res;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SavePointRes {
    private Long pointId;
    private Long totalPoint;
    private Long userId;
}
