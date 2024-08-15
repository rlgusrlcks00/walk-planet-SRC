package com.cero.cm.db.repository.steptotal;

import com.cero.cm.db.entity.StepTotal;
import com.cero.cm.db.entity.User;
import com.cero.cm.db.repository.steptotal.dsl.StepTotalRepositoryDsl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StepTotalRepository extends JpaRepository<StepTotal, Long>, StepTotalRepositoryDsl {
    Optional<StepTotal> findByUser(User user);
}
