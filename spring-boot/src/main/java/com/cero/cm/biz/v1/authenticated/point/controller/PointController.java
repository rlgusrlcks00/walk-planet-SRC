package com.cero.cm.biz.v1.authenticated.point.controller;

import com.cero.cm.biz.v1.authenticated.point.model.req.SavePointReq;
import com.cero.cm.biz.v1.authenticated.point.model.res.SavePointRes;
import com.cero.cm.biz.v1.authenticated.point.service.PointService;
import com.cero.cm.biz.v1.authenticated.step.model.req.SaveStepReq;
import com.cero.cm.biz.v1.authenticated.step.model.res.SaveStepRes;
import com.cero.cm.common.api.model.Response;
import com.cero.cm.common.type.ResultCodeConst;
import com.cero.cm.db.entity.Point;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.cero.cm.util.MessageUtils.messageSourceAccessor;

@RestController
@RequestMapping(path = "/point")
@CrossOrigin("*")
@RequiredArgsConstructor
@Api(tags = {"포인트"})
public class PointController {

    private final PointService pointService;

    @Operation(
            summary = "포인트 저장",
            description = "포인트 저장"
    )
    @PostMapping(path = "/save")
    public Response<SavePointRes> savePoint(
            @RequestParam Long point
    ) {
        Response<SavePointRes> res = new Response<>();
        try {
            SavePointReq req = new SavePointReq();
            req.setPoint(point);
            pointService.savePoint(req);
            SavePointRes savePointRes = pointService.getPointInfo();
            pointService.savePointHistory(point, savePointRes.getPointId());
            res.setResult(savePointRes);
            res.setResultCd(ResultCodeConst.SUCCESS.getCode());
            res.setResultMsg(messageSourceAccessor.getMessage(res.getResultCd()));
        } catch (Exception e){
            res.setResultCd(ResultCodeConst.FAIL.getCode());
            res.setResultMsg(e.getMessage());
        }
        return res;
    }

    @Operation(
            summary = "포인트 조회",
            description = "포인트 조회"
    )
    @GetMapping(path = "/info")
    public Response<SavePointRes> getPointInfo() {
        Response<SavePointRes> res = new Response<>();
        try {
            res.setResult(pointService.getPointInfo());
            res.setResultCd(ResultCodeConst.SUCCESS.getCode());
            res.setResultMsg(messageSourceAccessor.getMessage(res.getResultCd()));
        } catch (Exception e){
            res.setResultCd(ResultCodeConst.FAIL.getCode());
            res.setResultMsg(e.getMessage());
        }
        return res;
    }
}
