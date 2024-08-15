package com.cero.cm.biz.v1.authenticated.statistics.model.res;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class GetStatisticsRes {
    private Long userId;
    private Long stepCount;
    private Long todayStepGoals;
    private LocalDateTime regDt;
    private String dayOfWeek;
}
