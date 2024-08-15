package com.cero.cm.db.repository.steptotal.dsl;

import com.cero.cm.db.entity.QStepTotal;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;

@Aspect
@RequiredArgsConstructor
public class StepTotalRepositoryDslImpl implements StepTotalRepositoryDsl{

    private final JPAQueryFactory queryFactory;
    private final QStepTotal qStepTotal = QStepTotal.stepTotal;

    public Long findStepTotalCountByUserId(Long userId) {
        return queryFactory
                .select(qStepTotal.stepTotalCount)
                .from(qStepTotal)
                .where(qStepTotal.user.userId.eq(userId))
                .fetchOne();
    }

    public void updateStepTotal(Long userId, Long stepCount) {
        queryFactory
                .update(qStepTotal)
                .set(qStepTotal.stepTotalCount, stepCount)
                .where(qStepTotal.user.userId.eq(userId))
                .execute();
    }
}
