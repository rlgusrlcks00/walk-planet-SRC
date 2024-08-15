package com.cero.cm.biz.v1.authenticated.step.controller;

import com.cero.cm.biz.v1.authenticated.step.model.req.SaveStepGoalsReq;
import com.cero.cm.biz.v1.authenticated.step.model.req.SaveStepReq;
import com.cero.cm.biz.v1.authenticated.step.model.res.SaveStepRes;
import com.cero.cm.biz.v1.authenticated.step.service.StepService;
import com.cero.cm.common.api.model.Response;
import com.cero.cm.common.type.ResultCodeConst;
import com.cero.cm.db.entity.StepGoals;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.cero.cm.util.MessageUtils.messageSourceAccessor;

@RestController
@RequestMapping(path = "/step")
@CrossOrigin("*")
@RequiredArgsConstructor
@Api(tags = {"걸음 수"})
public class StepController {

    private final StepService stepService;
    @Operation(
            summary = "걸음 수 저장",
            description = "걸음 수 저장"
    )
    @PostMapping(path = "/save")
    public Response<SaveStepRes> saveStep(
            @RequestParam Long stepCount
    ) {
        Response<SaveStepRes> res = new Response<>();
        try {
            SaveStepReq req = new SaveStepReq();
            req.setStepCount(stepCount);
            stepService.saveStep(req);
            stepService.saveTotalStep(req);
            res.setResult(stepService.getStepInfo());
            res.setResultCd(ResultCodeConst.SUCCESS.getCode());
            res.setResultMsg(messageSourceAccessor.getMessage(res.getResultCd()));
        } catch (Exception e){
            res.setResultCd(ResultCodeConst.FAIL.getCode());
            res.setResultMsg(e.getMessage());
        }
        return res;
    }

    @Operation(
            summary = "걸음 수 조회",
            description = "걸음 수 조회"
    )
    @GetMapping(path = "/info")
    public Response<SaveStepRes> getStepInfo() {
        Response<SaveStepRes> res = new Response<>();
        try {
            res.setResult(stepService.getStepInfo());
            res.setResultCd(ResultCodeConst.SUCCESS.getCode());
            res.setResultMsg(messageSourceAccessor.getMessage(res.getResultCd()));
        } catch (Exception e){
            res.setResultCd(ResultCodeConst.FAIL.getCode());
            res.setResultMsg(e.getMessage());
        }
        return res;
    }

    @Operation(
            summary = "목표 걸음 수 저장",
            description = "목표 걸음 수 저장"
    )
    @PostMapping(path = "/savegoals")
    public Response<Boolean> saveStepGoals(
            @RequestParam Long stepGoalsCount
    ) {
        Response<Boolean> res = new Response<>();
        try {
            SaveStepGoalsReq req = SaveStepGoalsReq
                    .builder()
                    .stepGoalsCount(stepGoalsCount)
                    .build();
            boolean doSaveGoals = stepService.saveStepGoals(req);
            if(doSaveGoals)  {
                res.setResult(true);
            } else {
                res.setResult(false);
            }
            res.setResultCd(ResultCodeConst.SUCCESS.getCode());
            res.setResultMsg(messageSourceAccessor.getMessage(res.getResultCd()));
        } catch (Exception e){
            res.setResultCd(ResultCodeConst.FAIL.getCode());
            res.setResultMsg(e.getMessage());
        }
        return res;
    }

    @Operation(
            summary = "목표 걸음 수 조회",
            description = "목표 걸음 수 조회"
    )
    @GetMapping(path = "/goals")
    public Response<StepGoals> getStepGoals() {
        Response<StepGoals> res = new Response<>();
        try {
            res.setResult(stepService.getStepGoals());
            res.setResultCd(ResultCodeConst.SUCCESS.getCode());
            res.setResultMsg(messageSourceAccessor.getMessage(res.getResultCd()));
        } catch (Exception e){
            res.setResultCd(ResultCodeConst.FAIL.getCode());
            res.setResultMsg(e.getMessage());
        }
        return res;
    }
}
