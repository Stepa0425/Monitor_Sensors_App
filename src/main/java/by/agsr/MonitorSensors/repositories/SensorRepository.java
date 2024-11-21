package by.agsr.MonitorSensors.repositories;

import by.agsr.MonitorSensors.models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<Sensor, Long> {
}
