package com.cero.cm.util;

import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.PathBuilderFactory;
import com.querydsl.jpa.JPQLQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * queryDsl을 사용할 경우 공통 paging처리 해주는 util
 */
@Component
@RequiredArgsConstructor
public class JpaPageUtil {
    private final EntityManager entityManager;

    private Querydsl getQuerydsl(Class clazz) {    // 1)
        PathBuilder builder = new PathBuilderFactory().create(clazz);
        return new Querydsl(entityManager, builder);
    }

    public <T> PageImpl<T> getPageImpl(Pageable pageable, JPQLQuery<T> query, Class clazz) {    // 2)
        long totalCount = query.fetchCount();
        List<T> results = getQuerydsl(clazz).applyPagination(pageable, query).fetch();
        return new PageImpl<>(results, pageable, totalCount);
    }

    public <T> PageImpl<T> getPageImpl(Pageable pageable, JPQLQuery<T> query, Class clazz, boolean isPaging) {    // 2)
        if(isPaging) {
            return getPageImpl(pageable, query, clazz);
        } else {
            return new PageImpl<>(query.fetch());
        }
    }

}
