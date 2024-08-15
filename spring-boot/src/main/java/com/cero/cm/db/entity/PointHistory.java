package com.cero.cm.db.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "tb_point_history")
public class PointHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_history_id")
    private Long pointHistoryId;

    @Column(name = "point")
    private Long point;

    @Column(name = "point_id")
    private Long pointId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "reg_dt")
    private LocalDateTime regDt;

    @Column(name = "mod_dt")
    private LocalDateTime modDt;

    @Column(name = "event_enum_id")
    private Long eventEnumId;

    @Column(name = "event_type")
    private String eventType;

    @Builder
    public PointHistory(Long pointHistoryId, Long point, Long pointId, Long userId, LocalDateTime regDt, LocalDateTime modDt, Long eventEnumId, String eventType) {
        this.pointHistoryId = pointHistoryId;
        this.point = point;
        this.pointId = pointId;
        this.userId = userId;
        this.regDt = regDt;
        this.modDt = modDt;
        this.eventEnumId = eventEnumId;
        this.eventType = eventType;
    }
}
