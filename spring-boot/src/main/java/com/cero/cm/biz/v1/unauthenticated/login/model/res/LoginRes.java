package com.cero.cm.biz.v1.unauthenticated.login.model.res;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRes {
    private Long userId;
    private String userName;
    private String userEmail;
    private String token;
    private String refreshToken;
    private Long stepCount;
    private String isFirstTimeSetupDone;

    @Builder
    public LoginRes(Long userId, String userName, String userEmail, String token,String refreshToken , Long stepCount, String isFirstTimeSetupDone) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.token = token;
        this.refreshToken = refreshToken;
        this.stepCount = stepCount;
        this.isFirstTimeSetupDone = isFirstTimeSetupDone;
    }
}
