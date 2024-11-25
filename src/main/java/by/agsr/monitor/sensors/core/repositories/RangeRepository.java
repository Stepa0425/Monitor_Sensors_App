package by.agsr.monitor.sensors.core.repositories;

import by.agsr.monitor.sensors.core.models.Range;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RangeRepository extends JpaRepository<Range, Long> {
    @Query("""
            SELECT r FROM Range r
            WHERE r.rangeFrom = :rangeFrom
            AND r.rangeTo = :rangeTo
            """)
    Optional<Range> findByRangeFromAndRangeTo(@Param("rangeFrom") Integer rangeFrom, @Param("rangeTo") Integer rangeTo);

}
