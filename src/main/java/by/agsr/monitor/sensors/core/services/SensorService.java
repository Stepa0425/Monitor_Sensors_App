package by.agsr.monitor.sensors.core.services;

import by.agsr.monitor.sensors.core.api.dto.SensorRequestDTO;
import by.agsr.monitor.sensors.core.api.dto.SensorResponseDTO;

import java.util.List;

public interface SensorService {

    SensorResponseDTO createSensor(SensorRequestDTO sensorRequestDTO);

    List<SensorResponseDTO> getAllSensors();

    void deleteSensor(Long sensorId);

    SensorResponseDTO updateSensor(Long sensorId, SensorRequestDTO sensorRequestDTO);

    List<SensorResponseDTO> getSensorsByName(String name);

    List<SensorResponseDTO> getSensorsByModel(String model);
}
