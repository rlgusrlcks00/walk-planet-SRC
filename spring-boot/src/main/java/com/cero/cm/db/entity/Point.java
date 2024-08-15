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
@Table(name = "tb_point")
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_id")
    private Long pointId;

    @Column(name = "total_point")
    private Long totalPoint;

    @Column(name = "reg_dt")
    private LocalDateTime regDt;

    @Column(name = "mod_dt")
    private LocalDateTime modDt;

    @Column(name = "user_id")
    private Long userId;

    @Builder
    public Point(Long totalPoint, LocalDateTime regDt, LocalDateTime modDt, Long userId) {
        this.totalPoint = totalPoint;
        this.regDt = regDt;
        this.modDt = modDt;
        this.userId = userId;
    }
}
