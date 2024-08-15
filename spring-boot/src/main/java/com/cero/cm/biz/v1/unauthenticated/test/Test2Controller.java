package com.cero.cm.biz.v1.unauthenticated.test;

import com.cero.cm.common.api.model.Response;
import com.cero.cm.common.type.ResultCodeConst;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.cero.cm.util.MessageUtils.messageSourceAccessor;

@RestController
@RequestMapping(path = "/open/test2")
@CrossOrigin("*")
@RequiredArgsConstructor
@Api(tags = {"TEST2"})
public class Test2Controller {
    private final Test2Service test2Service;
    @Operation(
            summary = "test2",
            description = "test2"
    )
    @GetMapping(value = "/test")
    public Response<TestResponse> test() {

        Response<TestResponse> res = new Response<>();

        try {
            TestResponse result = test2Service.getTestResponse();
            res.setResult(result);
            res.setResultCd(ResultCodeConst.SUCCESS.getCode());
            res.setResultMsg(messageSourceAccessor.getMessage(res.getResultCd()));

        } catch (Exception e) {
            res.setResultCd(ResultCodeConst.FAIL.getCode());
            res.setResultMsg(e.getMessage());
        };
        return res;
    }

}
