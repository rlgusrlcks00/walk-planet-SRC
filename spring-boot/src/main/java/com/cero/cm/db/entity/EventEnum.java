package com.cero.cm.db.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "tb_event_enum")
public class EventEnum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_enum_id")
    private Long eventEnumId;

    @Column(name = "event_type")
    private String eventType;

    @Builder
    public EventEnum(String eventType) {
        this.eventType = eventType;
    }
}
