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
@Table(name = "tb_version")
public class Version {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "version_id")
    private Long versionId;

    @Column(name = "version")
    private double version;

    @Column(name = "build_number")
    private Long buildNumber;

    @Column(name = "reg_dt")
    private LocalDateTime regDt;

    @Builder
    public Version(double version, Long buildNumber, LocalDateTime regDt) {
        this.version = version;
        this.buildNumber = buildNumber;
        this.regDt = regDt;
    }
}
