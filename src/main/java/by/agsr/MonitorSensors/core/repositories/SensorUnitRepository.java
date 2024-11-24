package by.agsr.MonitorSensors.core.repositories;

import by.agsr.MonitorSensors.core.models.SensorUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SensorUnitRepository extends JpaRepository<SensorUnit, Long> {

    Optional<SensorUnit> findByName(String name);
}
