package com.cero.cm.biz.v1.unauthenticated.version.controller;

import com.cero.cm.biz.v1.unauthenticated.version.service.VersionService;
import com.cero.cm.common.api.model.Response;
import com.cero.cm.common.type.ResultCodeConst;
import com.cero.cm.db.entity.Version;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.cero.cm.util.MessageUtils.messageSourceAccessor;

@RestController
@RequestMapping(path = "/open/version")
@CrossOrigin("*")
@RequiredArgsConstructor
@Api(tags = {"버전 관리"})
public class VersionController {

    private final VersionService versionService;

    @Operation(
        summary = "버전 조회",
        description = "버전 조회"
    )
    @GetMapping(value="/get")
    public Response<Version> getVersion() {
        Response<Version> res = new Response<>();
        try {
            Version result = versionService.getVersion();
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
        summary = "버전 저장",
        description = "버전 저장"
    )
    @PostMapping(value="/save")
    public Response<Void> saveVersion(
            @RequestParam(value = "version") Double version,
            @RequestParam(value = "buildNumber") Long buildNumber
    ) {
        Response<Void> res = new Response<>();
        try {
            versionService.saveVersion(version, buildNumber);
            res.setResultCd(ResultCodeConst.SUCCESS.getCode());
            res.setResultMsg(messageSourceAccessor.getMessage(res.getResultCd()));
        } catch (Exception e) {
            res.setResultCd(ResultCodeConst.FAIL.getCode());
            res.setResultMsg(e.getMessage());
        }
        return res;
    }
}
