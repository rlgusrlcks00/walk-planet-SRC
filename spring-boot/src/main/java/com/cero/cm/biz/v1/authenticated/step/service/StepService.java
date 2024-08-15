package com.cero.cm.biz.v1.authenticated.step.service;

import com.cero.cm.biz.v1.authenticated.step.model.req.SaveStepGoalsReq;
import com.cero.cm.biz.v1.authenticated.step.model.req.SaveStepReq;
import com.cero.cm.biz.v1.authenticated.step.model.res.SaveStepRes;
import com.cero.cm.db.entity.StepGoals;
import com.cero.cm.db.entity.StepHistory;
import com.cero.cm.db.entity.StepTotal;
import com.cero.cm.db.entity.User;
import com.cero.cm.db.repository.stepgoals.StepGoalsRepository;
import com.cero.cm.db.repository.stephistory.StepHistoryRepository;
import com.cero.cm.db.repository.steptotal.StepTotalRepository;
import com.cero.cm.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StepService {

    private final StepTotalRepository stepTotalRepository;
    private final StepHistoryRepository stepHistoryRepository;
    private final StepGoalsRepository stepGoalsRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public SaveStepRes getStepInfo() {
        Long userId = TokenUtil.getUserId();
        LocalDateTime today = LocalDate.now().atStartOfDay();
        User user = User.builder().userId(userId).build();

        // StepTotal 정보 조회
        StepTotal stepTotal = stepTotalRepository.findByUser(user)
                .orElse(StepTotal.builder().stepTotalCount(0L).build());

        // StepHistory 정보 조회
        StepHistory stepHistory = stepHistoryRepository.findByUserIdAndRegDt(userId, today)
                .orElse(StepHistory.builder().stepCount(0L).build());

        return SaveStepRes.builder()
                .stepId(stepHistory.getStepId())
                .stepTotalCount(stepTotal.getStepTotalCount())
                .stepTodayCount(stepHistory.getStepCount())
                .regDt(stepHistory.getRegDt())
                .modDt(stepHistory.getModDt())
                .userId(userId)
                .build();
    }

    @Transactional
    public void saveStep(SaveStepReq req) {
        Long userId = TokenUtil.getUserId();
        LocalDateTime today = LocalDate.now().atStartOfDay();
        LocalDateTime now = LocalDateTime.now();

        logger.info("req history: {}", req.getStepCount());
        // StepHistory 정보 저장
        Optional<StepGoals> stepGoalsOpt = stepGoalsRepository.findByUserId(userId);
        if (stepGoalsOpt.isEmpty()) {
            return;
        }
        Long stepGoalsCount = stepGoalsOpt.get().getStepGoalsCount();
        Optional<StepHistory> stepHistoryOpt = stepHistoryRepository.findByUserIdAndRegDt(userId, today);
        if (stepHistoryOpt.isPresent()) {
            StepHistory stepHistory = stepHistoryOpt.get();
            stepHistory.setStepCount(req.getStepCount());
            stepHistory.setModDt(now);
            stepHistory.setTodayStepGoals(stepGoalsCount);
            stepHistoryRepository.save(stepHistory);
        } else {
            stepHistoryRepository.save(req.toStepHistoryEntity(userId, stepGoalsCount));
        }
    }

    @Transactional
    public void saveTotalStep(SaveStepReq req) {
        Long userId = TokenUtil.getUserId();
        LocalDateTime now = LocalDateTime.now();
        User user = User.builder().userId(userId).build();

        logger.info("req total: {}", req.getStepCount());
        List<Long> stepCountList = stepHistoryRepository.findStepCountByUserId(userId);
        long totalSteps = stepCountList.stream().mapToLong(Long::longValue).sum();
        // StepTotal 정보 저장
        Optional<StepTotal> stepTotalOpt = stepTotalRepository.findByUser(user);
        if (stepTotalOpt.isPresent()) {
            StepTotal stepTotal = stepTotalOpt.get();
            stepTotal.setStepTotalCount(totalSteps);
            stepTotal.setModDt(now);
            stepTotalRepository.save(stepTotal);
        } else {
            stepTotalRepository.save(req.toStepTotalEntity(user));
        }
    }

    @Transactional
    public boolean saveStepGoals(SaveStepGoalsReq req) {
        Long userId = TokenUtil.getUserId();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime today = LocalDate.now().atStartOfDay();

        Optional<StepGoals> stepGoalsOpt = stepGoalsRepository.findByUserId(userId);
        if(stepGoalsOpt.isPresent()){
            StepGoals stepGoals = stepGoalsOpt.get();
            if (stepGoals.getModDt().isAfter(today)) {
                return false;
            }
            stepGoals.setStepGoalsCount(req.getStepGoalsCount());
            stepGoals.setModDt(now);
            stepGoalsRepository.save(stepGoals);
        } else {
            stepGoalsRepository.save(req.toStepGoalsEntity(userId));
        }
        return true;
    }

    public StepGoals getStepGoals() {
        Long userId = TokenUtil.getUserId();
        Optional<StepGoals> stepGoalsOpt = stepGoalsRepository.findByUserId(userId);
        return stepGoalsOpt.orElse(StepGoals.builder().stepGoalsCount(0L).build());
    }
}
