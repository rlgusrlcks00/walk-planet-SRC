package com.cero.cm.biz.v1.unauthenticated.join.controller;

import com.cero.cm.biz.v1.authenticated.step.model.req.SaveStepGoalsReq;
import com.cero.cm.biz.v1.authenticated.step.service.StepService;
import com.cero.cm.biz.v1.unauthenticated.join.model.req.JoinReq;
import com.cero.cm.biz.v1.unauthenticated.join.service.JoinService;
import com.cero.cm.common.api.model.Response;
import com.cero.cm.common.type.ResultCodeConst;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.cero.cm.util.MessageUtils.messageSourceAccessor;

@RestController
@RequestMapping(path = "/open/join")
@CrossOrigin("*")
@RequiredArgsConstructor
@Api(tags = {"회원가입"})
public class JoinController {

    private final JoinService joinService;
    private final StepService stepService;

    @Operation(
            summary = "회원가입",
            description = "회원가입"
    )
    @PostMapping(path = "/user")
    public Response<String> joinUser(
            @RequestParam(value = "userEmail")  String userEmail,
            @RequestParam(value = "userName")  String userName,
            @RequestParam(value = "userPwd")  String userPwd
    ) {
        Response<String> res = new Response<>();
        try{
            // 회원가입
            JoinReq req = new JoinReq();
            req.setUserEmail(userEmail);
            req.setUserName(userName);
            req.setUserPwd(userPwd);
            String result = joinService.joinUser(req);

            res.setResult(result);
            res.setResultCd(ResultCodeConst.SUCCESS.getCode());
            res.setResultMsg(messageSourceAccessor.getMessage(res.getResultCd()));

        } catch (Exception e){
            res.setResultCd(ResultCodeConst.FAIL.getCode());
            res.setResultMsg(e.getMessage());
        }

        return res;
    }
}
