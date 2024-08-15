package com.cero.cm.biz.v1.authenticated.deleteaccount.controller;

import com.cero.cm.biz.v1.authenticated.deleteaccount.service.DeleteAccountService;
import com.cero.cm.common.api.model.Response;
import com.cero.cm.common.type.ResultCodeConst;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.cero.cm.util.MessageUtils.messageSourceAccessor;

@RestController
@RequestMapping(path = "/delete")
@CrossOrigin("*")
@RequiredArgsConstructor
@Api(tags = {"회원 탈퇴"})
public class DeleteAccountController {

    private final DeleteAccountService deleteAccountService;

    @Operation(
            summary = "회원 탈퇴\"",
            description = "회원 탈퇴\""
    )
    @PostMapping(path = "/account")
    public Response<String> deleteAccount() {
        Response<String> res = new Response<>();
        try {
            String result = deleteAccountService.deleteAccount();
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
