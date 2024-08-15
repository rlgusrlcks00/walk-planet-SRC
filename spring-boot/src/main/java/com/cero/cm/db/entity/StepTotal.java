package com.cero.cm.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "tb_step_total", uniqueConstraints = {@UniqueConstraint(columnNames = "user_id")})
public class StepTotal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "step_total_id")
    private Long stepTotalId;

    @Column(name = "step_total_count")
    private Long stepTotalCount;

    @Column(name = "reg_dt")
    private LocalDateTime regDt;

    @Column(name = "mod_dt")
    private LocalDateTime modDt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @Builder
    public StepTotal(Long stepTotalId, Long stepTotalCount, LocalDateTime regDt, LocalDateTime modDt, User user) {
        this.stepTotalId = stepTotalId;
        this.stepTotalCount = stepTotalCount;
        this.regDt = regDt;
        this.modDt = modDt;
        this.user = user;
    }
}
