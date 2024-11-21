package by.agsr.MonitorSensors.repositories;

import by.agsr.MonitorSensors.models.SensorType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorTypeRepository extends JpaRepository<SensorType, Long> {
}
