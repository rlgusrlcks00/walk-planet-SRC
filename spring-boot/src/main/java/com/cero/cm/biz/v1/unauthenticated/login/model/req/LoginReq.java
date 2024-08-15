package com.cero.cm.biz.v1.unauthenticated.login.model.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginReq {
    private String userEmail;
    private String userPwd;
}
