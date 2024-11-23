package by.agsr.MonitorSensors.core.services;

import by.agsr.MonitorSensors.core.dto.SensorRequestDTO;
import by.agsr.MonitorSensors.core.dto.SensorResponseDTO;

public interface SensorService {

    SensorResponseDTO createSensor(SensorRequestDTO sensorRequestDTO);
}
