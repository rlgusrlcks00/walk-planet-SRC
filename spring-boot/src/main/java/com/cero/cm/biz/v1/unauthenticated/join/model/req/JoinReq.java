package com.cero.cm.biz.v1.unauthenticated.join.model.req;

import com.cero.cm.db.entity.StepTotal;
import com.cero.cm.db.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class JoinReq {
    private String userName;
    private String userPwd;
    private String userEmail;

    public User toUserEntity() {
        return User.builder()
                .userName(userName)
                .userPwd(userPwd)
                .userEmail(userEmail)
                .regDt(LocalDateTime.now())
                .modDt(LocalDateTime.now())
                .isFirstTimeSetupDone("N")
                .delYn("N")
                .build();
    }

    public StepTotal toStepTotalEntity(User user) {
        return StepTotal.builder()
                .stepTotalCount(0L)
                .regDt(LocalDateTime.now())
                .modDt(LocalDateTime.now())
                .user(user)
                .build();
    }
}
