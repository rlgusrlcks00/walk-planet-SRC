package com.cero.cm.biz.v1.authenticated.step.model.req;

import com.cero.cm.db.entity.StepGoals;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class SaveStepGoalsReq {
    private Long stepGoalsCount;

    public StepGoals toStepGoalsEntity(Long userId) {
        LocalDateTime now = LocalDateTime.now();

        return StepGoals.builder()
                .stepGoalsCount(stepGoalsCount)
                .userId(userId)
                .regDt(now)
                .modDt(now)
                .build();
    }
}
