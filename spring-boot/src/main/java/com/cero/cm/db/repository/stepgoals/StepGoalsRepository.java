package com.cero.cm.db.repository.stepgoals;

import com.cero.cm.db.entity.StepGoals;
import com.cero.cm.db.repository.stepgoals.dsl.StepGoalsRepositoryDsl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StepGoalsRepository extends JpaRepository<StepGoals, Long> , StepGoalsRepositoryDsl {
    Optional<StepGoals> findByUserId(Long userId);
}
