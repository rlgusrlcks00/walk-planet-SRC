package com.cero.cm.db.repository.point.dsl;

import com.cero.cm.db.entity.QStepHistory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;

@Aspect
@RequiredArgsConstructor
public class PointRepositoryDslImpl implements PointRepositoryDsl {
    private final JPAQueryFactory queryFactory;
    private final QStepHistory qStepHistory = QStepHistory.stepHistory;
}
