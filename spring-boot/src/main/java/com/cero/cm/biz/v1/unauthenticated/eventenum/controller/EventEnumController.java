package com.cero.cm.biz.v1.unauthenticated.eventenum.controller;

import com.cero.cm.biz.v1.unauthenticated.eventenum.model.req.EventEnumReq;
import com.cero.cm.biz.v1.unauthenticated.eventenum.service.EventEnumService;
import com.cero.cm.common.api.model.Response;
import com.cero.cm.common.type.ResultCodeConst;
import com.cero.cm.db.entity.EventEnum;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.cero.cm.util.MessageUtils.messageSourceAccessor;

@RestController
@RequestMapping(path = "/open/enum")
@CrossOrigin("*")
@RequiredArgsConstructor
@Api(tags = {"ENUM 값 설정"})
public class EventEnumController {

    private final EventEnumService eventEnumService;

    @Operation(
            summary = "ENUM 값 설정",
            description = "ENUM 값 설정"
    )
    @PostMapping(path = "/set")
    public Response<Void> setEventEnum(
            @RequestParam(value = "eventType") String eventType
    ) {
        Response<Void> res = new Response<>();
        try {
            EventEnumReq eventEnumReq = EventEnumReq
                    .builder()
                    .eventType(eventType)
                    .build();
            eventEnumService.setEventEnum(eventEnumReq);
            res.setResultCd(ResultCodeConst.SUCCESS.getCode());
            res.setResultMsg(messageSourceAccessor.getMessage(res.getResultCd()));

        } catch (Exception e){
            res.setResultCd(ResultCodeConst.FAIL.getCode());
            res.setResultMsg(e.getMessage());
        }
        return res;
    }

    @Operation(
            summary = "ENUM 값 조회",
            description = "ENUM 값 조회"
    )
    @GetMapping(path = "/get")
    public Response<List<EventEnum>> getEventEnum() {
        Response<List<EventEnum>> res = new Response<>();
        try {
            res.setResult(eventEnumService.getEventEnum());
            res.setResultCd(ResultCodeConst.SUCCESS.getCode());
            res.setResultMsg(messageSourceAccessor.getMessage(res.getResultCd()));
        } catch (Exception e){
            res.setResultCd(ResultCodeConst.FAIL.getCode());
            res.setResultMsg(e.getMessage());
        }
        return res;
    }
}
