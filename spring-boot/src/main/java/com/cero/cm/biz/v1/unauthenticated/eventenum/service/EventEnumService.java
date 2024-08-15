package com.cero.cm.biz.v1.unauthenticated.eventenum.service;

import com.cero.cm.biz.v1.unauthenticated.eventenum.model.req.EventEnumReq;
import com.cero.cm.db.entity.EventEnum;
import com.cero.cm.db.repository.eventenum.EventEnumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventEnumService {
    private final EventEnumRepository eventEnumRepository;
    @Transactional
    public void setEventEnum(EventEnumReq eventEnumReq) {
        eventEnumRepository.save(eventEnumReq.toEntity());
    }

    public List<EventEnum> getEventEnum() {
        return eventEnumRepository.findAll();
    }
}
