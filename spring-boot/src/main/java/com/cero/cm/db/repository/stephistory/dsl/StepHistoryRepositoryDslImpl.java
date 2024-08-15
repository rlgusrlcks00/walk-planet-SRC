package com.cero.cm.db.repository.stephistory.dsl;

import com.cero.cm.biz.v1.authenticated.statistics.model.res.GetStatisticsRes;
import com.cero.cm.db.entity.QPointHistory;
import com.cero.cm.db.entity.QStepHistory;
import com.cero.cm.db.entity.StepHistory;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Aspect
@RequiredArgsConstructor
public class StepHistoryRepositoryDslImpl implements StepHistoryRepositoryDsl {

    private final JPAQueryFactory queryFactory;
    private final QStepHistory qStepHistory = QStepHistory.stepHistory;
    private final QPointHistory qPointHistory = QPointHistory.pointHistory;

    public Optional<StepHistory> findByUserIdAndRegDt(Long userId, LocalDateTime today) {
        JPQLQuery<StepHistory> query = queryFactory
                .select(qStepHistory)
                .from(qStepHistory)
                .where(qStepHistory.user.userId.eq(userId)
                        .and(qStepHistory.regDt.gt(today)));
        return Optional.ofNullable(query.fetchOne());
    }

    public List<Long> findStepCountByUserId(Long userId) {
        JPQLQuery<Long> query = queryFactory
                .select(qStepHistory.stepCount)
                .from(qStepHistory)
                .where(qStepHistory.user.userId.eq(userId));
        return query.fetch();
    }

    public Long findStepCountByUserIdAndRegDt(Long userId, LocalDateTime regDt) {
        JPQLQuery<Long> query = queryFactory
                .select(qStepHistory.stepCount)
                .from(qStepHistory)
                .where(qStepHistory.user.userId.eq(userId)
                        .and(qStepHistory.regDt.gt(regDt)));
        return query.fetchOne();
    }

    public List<GetStatisticsRes> getStatistics(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        JPQLQuery<GetStatisticsRes> query = queryFactory
                .select(Projections.fields(GetStatisticsRes.class,
                        qStepHistory.user.userId,qStepHistory.stepCount,qStepHistory.todayStepGoals,qStepHistory.regDt))
                .from(qStepHistory)
                .where(qStepHistory.user.userId.eq(userId)
                        .and(qStepHistory.regDt.between(startDate, endDate)))
                .orderBy(qStepHistory.regDt.asc());
        return query.fetch();
    }
}
