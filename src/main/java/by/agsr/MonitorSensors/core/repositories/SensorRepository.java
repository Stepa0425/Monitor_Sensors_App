package by.agsr.MonitorSensors.core.repositories;

import by.agsr.MonitorSensors.core.models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long> {

    List<Sensor> findByNameContaining(String name);

    List<Sensor> findByModelContaining(String model);
}
