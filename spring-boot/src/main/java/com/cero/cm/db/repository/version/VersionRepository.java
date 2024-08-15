package com.cero.cm.db.repository.version;

import com.cero.cm.db.entity.Version;
import com.cero.cm.db.repository.version.dsl.VersionRepositoryDsl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VersionRepository extends JpaRepository<Version, Long>, VersionRepositoryDsl {
    Version findFirstByOrderByRegDtDesc();
}
