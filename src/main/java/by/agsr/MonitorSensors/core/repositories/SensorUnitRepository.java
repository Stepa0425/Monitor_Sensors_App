package by.agsr.MonitorSensors.core.repositories;

import by.agsr.MonitorSensors.core.models.SensorUnit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SensorUnitRepository extends JpaRepository<SensorUnit, Long> {

    Optional<SensorUnit> findByName(String name);
}
