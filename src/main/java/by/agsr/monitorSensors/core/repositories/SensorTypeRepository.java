package by.agsr.monitorSensors.core.repositories;

import by.agsr.monitorSensors.core.models.SensorType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SensorTypeRepository extends JpaRepository<SensorType, Long> {

    Optional<SensorType> findByName(String name);
}
