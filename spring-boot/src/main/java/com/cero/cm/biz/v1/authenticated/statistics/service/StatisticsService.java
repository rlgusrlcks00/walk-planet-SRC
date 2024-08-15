package com.cero.cm.biz.v1.authenticated.statistics.service;

import com.cero.cm.biz.v1.authenticated.statistics.model.req.GetStatisticsReq;
import com.cero.cm.biz.v1.authenticated.statistics.model.res.GetStatisticsRes;
import com.cero.cm.db.repository.stephistory.StepHistoryRepository;
import com.cero.cm.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final StepHistoryRepository stepHistoryRepository;

    public List<GetStatisticsRes> getStatistics(GetStatisticsReq req) {
        Long userId = TokenUtil.getUserId();

        List<GetStatisticsRes> result = stepHistoryRepository.getStatistics(userId, req.getStartDate(), req.getEndDate());

        Map<LocalDateTime, GetStatisticsRes> resultMap = new HashMap<>();
        LocalDateTime current = req.getStartDate().toLocalDate().atStartOfDay();
        LocalDateTime end = req.getEndDate().toLocalDate().atStartOfDay();

        while (!current.isAfter(end)) {
            GetStatisticsRes res = new GetStatisticsRes();
            res.setRegDt(current);
            res.setDayOfWeek(current.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN).substring(0, 1)); // 요일을 한 글자로 설정
            res.setStepCount(0L);
            res.setTodayStepGoals(0L);
            resultMap.put(current, res);
            current = current.plusDays(1);
        }

        result.forEach(res -> {
            if (res.getRegDt() != null) {
                LocalDateTime dateOnly = res.getRegDt().toLocalDate().atStartOfDay();
                if (resultMap.containsKey(dateOnly)) {
                    res.setDayOfWeek(dateOnly.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN).substring(0, 1)); // 요일을 한 글자로 설정
                    resultMap.put(dateOnly, res);
                }
            }
        });

        return resultMap.values().stream()
                .sorted(Comparator.comparing(GetStatisticsRes::getRegDt))
                .collect(Collectors.toList());
    }
}
