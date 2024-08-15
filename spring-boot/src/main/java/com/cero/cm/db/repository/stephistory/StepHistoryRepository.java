package com.cero.cm.db.repository.stephistory;

import com.cero.cm.db.entity.StepHistory;
import com.cero.cm.db.repository.stephistory.dsl.StepHistoryRepositoryDsl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StepHistoryRepository extends JpaRepository<StepHistory, Long>, StepHistoryRepositoryDsl {
}
