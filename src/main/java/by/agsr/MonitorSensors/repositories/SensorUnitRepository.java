package by.agsr.MonitorSensors.repositories;

import by.agsr.MonitorSensors.models.SensorUnit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SensorUnitRepository extends JpaRepository<SensorUnit, Long> {

    Optional<SensorUnit> findByName(String name);
}
