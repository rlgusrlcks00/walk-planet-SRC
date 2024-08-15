package com.cero.cm.db.repository.point;

import com.cero.cm.db.entity.Point;
import com.cero.cm.db.repository.point.dsl.PointRepositoryDsl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface PointRepository extends JpaRepository<Point, Long>,PointRepositoryDsl {
    Optional<Point> findByUserId(Long userId);
}
