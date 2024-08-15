package com.cero.cm.common.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * 기본 이력 Entity Class.
 *  - JPA Entity 클래스들이 해당 추상 클래스를 상속할 경우 regDt, modDt를 컬럼으로 인식
 *  - AuditingEntityListener.class에 Auditing 기능이 포함 됌.
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseHisEntity {

    @Column(name = "reg_dt", updatable = false)
    @CreatedDate
    private LocalDateTime regDt;

    @Column(name = "reg_id", updatable = false)
    @CreatedBy
    private Long regId;
}
