package com.cero.cm.biz.v1.authenticated.step.model.res;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class SaveStepRes {
    private Long stepId;
    private Long stepTotalCount;
    private Long stepTodayCount;
    private LocalDateTime regDt;
    private LocalDateTime modDt;
    private Long userId;
}
