package com.cero.cm.biz.v1.authenticated.user.controller;

import com.cero.cm.biz.v1.authenticated.point.model.req.SavePointReq;
import com.cero.cm.biz.v1.authenticated.point.model.res.SavePointRes;
import com.cero.cm.biz.v1.authenticated.user.model.req.SaveUserProfileReq;
import com.cero.cm.biz.v1.authenticated.user.service.UserProfileService;
import com.cero.cm.common.api.model.Response;
import com.cero.cm.common.service.FileStorageService;
import com.cero.cm.common.type.ResultCodeConst;
import com.cero.cm.db.entity.User;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.cero.cm.util.MessageUtils.messageSourceAccessor;

@RestController
@RequestMapping(path = "/profile")
@CrossOrigin("*")
@RequiredArgsConstructor
@Api(tags = {"유저 프로필"})
public class UserPofileController {

    private final UserProfileService userProfileService;
    @Operation(
            summary = "유저 프로필 설정 저장",
            description = "유저 프로필 설정 저장"
    )
    @PostMapping(path = "/save")
    public Response<User> saveUserProfile(
            @RequestParam(value = "birth", required = false)  String birth,
            @RequestParam(value = "gender", required = false)  String gender,
            @RequestPart(value = "profileImg", required = false) MultipartFile profileImg,
            @RequestParam(value = "nickname", required = false)  String nickname,
            @RequestParam(value = "height", required = false)  Long height,
            @RequestParam(value = "weight", required = false)  Long weight,
            @RequestParam(value = "weightGoals", required = false)  Long weightGoals
    ) {
        Response<User> res = new Response<>();
        try {
            SaveUserProfileReq req = new SaveUserProfileReq();

            req.setBirth(birth);
            req.setGender(gender);
            req.setNickname(nickname);
            req.setHeight(height);
            req.setWeight(weight);
            req.setWeightGoals(weightGoals);
            req.setIsFirstTimeSetupDone("Y");

            if(profileImg != null) {
                System.out.println("============================profileImg : " + profileImg.getOriginalFilename());
                req.setProfileImg(profileImg);
            }

            User result = userProfileService.saveUserProfile(req);

            res.setResult(result);
            res.setResultCd(ResultCodeConst.SUCCESS.getCode());
            res.setResultMsg(messageSourceAccessor.getMessage(res.getResultCd()));
        } catch (Exception e){
            res.setResultCd(ResultCodeConst.FAIL.getCode());
            res.setResultMsg(e.getMessage());
        }
        return res;
    }

    @Operation(
            summary = "유저 프로필 조회",
            description = "유저 프로필 조회"
    )
    @GetMapping(path = "/info")
    public Response<User> getUserProfile() {
        Response<User> res = new Response<>();
        try {
            res.setResult(userProfileService.getUserProfile());
            res.setResultCd(ResultCodeConst.SUCCESS.getCode());
            res.setResultMsg(messageSourceAccessor.getMessage(res.getResultCd()));
        } catch (Exception e){
            res.setResultCd(ResultCodeConst.FAIL.getCode());
            res.setResultMsg(e.getMessage());
        }
        return res;
    }
}
