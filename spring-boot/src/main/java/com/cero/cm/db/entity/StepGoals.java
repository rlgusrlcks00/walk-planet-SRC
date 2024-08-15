package com.cero.cm.db.entity;

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
@Table(name = "tb_step_goals")
public class StepGoals {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "step_goals_id")
    private Long stepGoalsId;

    @Column(name = "step_goals_count")
    private Long stepGoalsCount;

    @Column(name = "reg_dt")
    private LocalDateTime regDt;

    @Column(name = "mod_dt")
    private LocalDateTime modDt;

    @Column(name = "user_id")
    private Long userId;

    @Builder
    public StepGoals(Long stepGoalsCount, LocalDateTime regDt, LocalDateTime modDt, Long userId) {
        this.stepGoalsCount = stepGoalsCount;
        this.regDt = regDt;
        this.modDt = modDt;
        this.userId = userId;
    }
}
