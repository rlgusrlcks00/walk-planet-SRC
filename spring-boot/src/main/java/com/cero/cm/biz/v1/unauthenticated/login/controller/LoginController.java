package com.cero.cm.biz.v1.unauthenticated.login.controller;

import com.cero.cm.biz.v1.unauthenticated.login.model.req.LoginReq;
import com.cero.cm.biz.v1.unauthenticated.login.model.res.LoginRes;
import com.cero.cm.biz.v1.unauthenticated.login.service.LoginService;
import com.cero.cm.common.api.model.Response;
import com.cero.cm.common.type.ResultCodeConst;
import com.cero.cm.config.config.JwtResponse;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static com.cero.cm.util.MessageUtils.messageSourceAccessor;

@RestController
@RequestMapping(path = "/open/login")
@CrossOrigin("*")
@RequiredArgsConstructor
@Api(tags = {"로그인"})
public class LoginController {

    private final LoginService loginService;
    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Operation(
            summary = "로그인",
            description = "로그인"
    )
    @GetMapping(value = "/user")
    public Response<LoginRes> loginUser(
            @RequestParam(value = "userEmail")  String userEmail,
            @RequestParam(value = "userPwd")  String userPwd
    ) {
        Response<LoginRes> res = new Response<>();
        try {
            LoginReq req = new LoginReq();
            req.setUserEmail(userEmail);
            req.setUserPwd(userPwd);
            LoginRes result = loginService.loginUser(req);

            logger.info(
                    "\n============================================================================================\n" +
                            "User ID : {} \n" +
                            "User Name : {} \n" +
                            "Token : Bearer {} \n" +
                            "Refresh Token: Bearer {} \n" +
                            "============================================================================================\n",
                    result.getUserId(),
                    result.getUserName(),
                    result.getToken(),
                    result.getRefreshToken()
            );

            res.setResult(result);
            res.setResultCd(ResultCodeConst.SUCCESS.getCode());
            res.setResultMsg(messageSourceAccessor.getMessage(res.getResultCd()));

        } catch (Exception e) {
            res.setResultCd(ResultCodeConst.FAIL.getCode());
            res.setResultMsg(e.getMessage());
        }
        return res;
    }

    @Operation(
            summary = "test",
            description = "test"
    )
    @GetMapping(value = "/test")
    public String test() {
        return "test";
    }

}
