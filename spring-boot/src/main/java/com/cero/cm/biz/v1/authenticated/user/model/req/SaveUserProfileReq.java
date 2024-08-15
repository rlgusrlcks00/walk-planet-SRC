package com.cero.cm.biz.v1.authenticated.user.model.req;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class SaveUserProfileReq {
    private String birth;
    private String gender;
    private MultipartFile profileImg;
    private String nickname;
    private Long height;
    private Long weight;
    private Long weightGoals;
    private String isFirstTimeSetupDone;
}
