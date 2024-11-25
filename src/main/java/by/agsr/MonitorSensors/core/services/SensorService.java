package by.agsr.MonitorSensors.core.services;

import by.agsr.MonitorSensors.core.dto.SensorRequestDTO;
import by.agsr.MonitorSensors.core.dto.SensorResponseDTO;

import java.util.List;

public interface SensorService {

    SensorResponseDTO createSensor(SensorRequestDTO sensorRequestDTO);

    List<SensorResponseDTO> getAllSensors();

    void deleteSensor(Long sensorId);

    SensorResponseDTO updateSensor(Long sensorId, SensorRequestDTO sensorRequestDTO);

    List<SensorResponseDTO> getByName(String name);

    List<SensorResponseDTO> getByModel(String model);
}
