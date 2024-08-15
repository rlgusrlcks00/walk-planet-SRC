package com.cero.cm.db.repository.stephistory.dsl;

import com.cero.cm.biz.v1.authenticated.statistics.model.res.GetStatisticsRes;
import com.cero.cm.db.entity.StepHistory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface StepHistoryRepositoryDsl {

    Optional<StepHistory> findByUserIdAndRegDt(Long userId, LocalDateTime today);

    List<Long> findStepCountByUserId(Long userId);

    Long findStepCountByUserIdAndRegDt(Long userId, LocalDateTime regDt);

    List<GetStatisticsRes> getStatistics(Long userId, LocalDateTime startDate, LocalDateTime endDate);
}
