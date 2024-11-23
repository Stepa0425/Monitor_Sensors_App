package by.agsr.MonitorSensors.core.repositories;

import by.agsr.MonitorSensors.core.models.SensorType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SensorTypeRepository extends JpaRepository<SensorType, Long> {

    Optional<SensorType> findByName(String name);
}
