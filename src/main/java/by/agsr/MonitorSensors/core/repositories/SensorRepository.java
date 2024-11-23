package by.agsr.MonitorSensors.core.repositories;

import by.agsr.MonitorSensors.core.models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<Sensor, Long> {
}
