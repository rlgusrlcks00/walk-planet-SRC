package com.cero.cm.biz.v1.authenticated.point.model.req;

import com.cero.cm.db.entity.Point;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SavePointReq {
    private Long point;

    public Point toPointEntity(Long userId, LocalDateTime now) {
        return Point.builder()
                .userId(userId)
                .totalPoint(point)
                .regDt(now)
                .modDt(now)
                .build();
    }
}
