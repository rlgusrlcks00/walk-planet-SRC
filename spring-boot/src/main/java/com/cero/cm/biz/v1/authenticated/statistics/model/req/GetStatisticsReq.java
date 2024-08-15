package com.cero.cm.biz.v1.authenticated.statistics.model.req;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class GetStatisticsReq {
    LocalDateTime startDate;
    LocalDateTime endDate;
}
