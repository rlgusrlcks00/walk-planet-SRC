package com.cero.cm.db.repository.user.dsl;

import com.cero.cm.db.entity.QUser;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;

import static com.cero.cm.db.entity.QUser.user;

@Aspect
@RequiredArgsConstructor
public class UserRepositoryDslImpl implements UserRepositoryDsl{

    private final JPAQueryFactory queryFactory;

    private final QUser qUser = QUser.user;

    public String findByToken(String token) {
        JPQLQuery<String> query = queryFactory
                .select(qUser.refreshToken)
                .from(qUser)
                .where(qUser.token.eq(token));
        return query.fetchOne();
    }
}
