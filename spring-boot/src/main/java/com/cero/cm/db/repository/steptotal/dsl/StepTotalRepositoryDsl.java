package com.cero.cm.db.repository.steptotal.dsl;

public interface StepTotalRepositoryDsl {

    Long findStepTotalCountByUserId(Long userId);

    void updateStepTotal(Long userId, Long stepCount);
}
