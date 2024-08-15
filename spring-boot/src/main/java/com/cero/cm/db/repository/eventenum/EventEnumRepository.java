package com.cero.cm.db.repository.eventenum;

import com.cero.cm.db.entity.EventEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventEnumRepository extends JpaRepository<EventEnum, Long> {
}
