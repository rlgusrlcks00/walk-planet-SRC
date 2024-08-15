package com.cero.cm.biz.v1.authenticated.step.model.req;

import com.cero.cm.db.entity.StepHistory;
import com.cero.cm.db.entity.StepTotal;
import com.cero.cm.db.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SaveStepReq {
    private Long stepCount;

    public StepHistory toStepHistoryEntity(Long userId, Long stepGoalsCount) {
        LocalDateTime now = LocalDateTime.now();
        return StepHistory.builder()
                .stepCount(stepCount)
                .regDt(now)
                .modDt(now)
                .todayStepGoals(stepGoalsCount)
                .user(User.builder().userId(userId).build())
                .build();
    }

    public StepTotal toStepTotalEntity(User user) {
        LocalDateTime now = LocalDateTime.now();
        return StepTotal.builder()
                .stepTotalCount(stepCount)
                .regDt(now)
                .modDt(now)
                .user(user)
                .build();
    }
}
