package by.agsr.MonitorSensors.repositories;

import by.agsr.MonitorSensors.models.SensorType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SensorTypeRepository extends JpaRepository<SensorType, Long> {

    Optional<SensorType> findByName(String name);
}
