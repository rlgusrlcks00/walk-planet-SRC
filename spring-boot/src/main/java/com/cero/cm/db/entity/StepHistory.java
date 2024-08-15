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
@Table(name = "tb_step_history")
public class StepHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "step_id")
    private Long stepId;

    @Column(name = "step_count")
    private Long stepCount;

    @Column(name = "reg_dt")
    private LocalDateTime regDt;

    @Column(name = "mod_dt")
    private LocalDateTime modDt;

    @Column(name = "today_step_goals")
    private Long todayStepGoals;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @Builder
    public StepHistory(Long stepId, Long stepCount, LocalDateTime regDt, LocalDateTime modDt, User user, Long todayStepGoals){
        this.stepId = stepId;
        this.stepCount = stepCount;
        this.regDt = regDt;
        this.modDt = modDt;
        this.user = user;
        this.todayStepGoals = todayStepGoals;
    }
}
