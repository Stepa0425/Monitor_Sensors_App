package by.agsr.MonitorSensors.core.repositories;

import by.agsr.MonitorSensors.core.models.SensorType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SensorTypeRepository extends JpaRepository<SensorType, Long> {

    Optional<SensorType> findByName(String name);
}
