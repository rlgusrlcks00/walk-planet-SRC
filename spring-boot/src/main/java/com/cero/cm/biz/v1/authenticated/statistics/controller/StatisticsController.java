package com.cero.cm.biz.v1.authenticated.statistics.controller;

import com.cero.cm.biz.v1.authenticated.statistics.model.req.GetStatisticsReq;
import com.cero.cm.biz.v1.authenticated.statistics.model.res.GetStatisticsRes;
import com.cero.cm.biz.v1.authenticated.statistics.service.StatisticsService;
import com.cero.cm.common.api.model.Response;
import com.cero.cm.common.type.ResultCodeConst;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.cero.cm.util.MessageUtils.messageSourceAccessor;

@RestController
@RequestMapping(path = "/statistics")
@CrossOrigin("*")
@RequiredArgsConstructor
@Api(tags = {"걸음 통계"})
public class StatisticsController {

    private final StatisticsService statisticsService;

    @Operation(
            summary = "통계 조회",
            description = "통계 조회"
    )
    @GetMapping(path = "/get")
    public Response<List<GetStatisticsRes>> getStatistics(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @Parameter(description = "Start date in the format yyyy-MM-ddTHH:mm:ss", example = "2023-07-01T00:00:00")
            @Schema(type = "string", format = "date-time")
            LocalDateTime startDate,

            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @Parameter(description = "End date in the format yyyy-MM-ddTHH:mm:ss", example = "2023-07-01T23:59:59")
            @Schema(type = "string", format = "date-time")
            LocalDateTime endDate
    ) {
        Response<List<GetStatisticsRes>> res = new Response<>();
        try {
            GetStatisticsReq req = new GetStatisticsReq();
            req.setStartDate(startDate);
            req.setEndDate(endDate);

            res.setResult(statisticsService.getStatistics(req));
            res.setResultCd(ResultCodeConst.SUCCESS.getCode());
            res.setResultMsg(messageSourceAccessor.getMessage(res.getResultCd()));
        } catch (Exception e){
            res.setResultCd(ResultCodeConst.FAIL.getCode());
            res.setResultMsg(e.getMessage());
        }
        return res;
    }
}
