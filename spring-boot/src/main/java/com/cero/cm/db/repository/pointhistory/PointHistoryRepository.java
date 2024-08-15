package com.cero.cm.db.repository.pointhistory;

import com.cero.cm.db.entity.PointHistory;
import com.cero.cm.db.repository.pointhistory.dsl.PointHistoryRepositoryDsl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointHistoryRepository extends JpaRepository<PointHistory, Long>, PointHistoryRepositoryDsl {
}
