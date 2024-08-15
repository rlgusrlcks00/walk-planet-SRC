package com.cero.cm.biz.v1.authenticated.point.service;

import com.cero.cm.biz.v1.authenticated.point.model.req.SavePointReq;
import com.cero.cm.biz.v1.authenticated.point.model.res.SavePointRes;
import com.cero.cm.db.entity.Point;
import com.cero.cm.db.entity.PointHistory;
import com.cero.cm.db.repository.point.PointRepository;
import com.cero.cm.db.repository.pointhistory.PointHistoryRepository;
import com.cero.cm.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PointService {
    private final PointRepository pointRepository;
    private final PointHistoryRepository pointHistoryRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public SavePointRes getPointInfo() {
        Long userId = TokenUtil.getUserId();
        Optional<Point> pointOpt = pointRepository.findByUserId(userId);
        if(pointOpt.isEmpty()) {
            return SavePointRes.builder()
                    .pointId(0L)
                    .userId(userId)
                    .totalPoint(0L)
                    .build();
        }
        return SavePointRes.builder()
                .userId(userId)
                .totalPoint(pointOpt.orElse(Point.builder().totalPoint(0L).build()).getTotalPoint())
                .pointId(pointOpt.get().getPointId())
                .build();
    }

    @Transactional
    public void savePoint(SavePointReq savePointReq) {
        Long userId = TokenUtil.getUserId();
        LocalDateTime now = LocalDateTime.now();
        Optional<Point> pointOpt = pointRepository.findByUserId(userId);
        if(pointOpt.isPresent()) {
            Point point = pointOpt.get();
            point.setTotalPoint(point.getTotalPoint() + savePointReq.getPoint());
            point.setModDt(now);
            pointRepository.save(point);
        } else {
            pointRepository.save(savePointReq.toPointEntity(userId, now));
        }
    }

    @Transactional
    public void savePointHistory(Long point, Long pointId) {
        Long userId = TokenUtil.getUserId();
        LocalDateTime now = LocalDateTime.now();
        PointHistory pointHistory = PointHistory.builder()
                .point(point)
                .pointId(pointId)
                .userId(userId)
                .regDt(now)
                .modDt(now)
                .eventEnumId(4L)
                .eventType("SAVE_POINT_BY_STEP")
                .build();
        pointHistoryRepository.save(pointHistory);
    }
}
