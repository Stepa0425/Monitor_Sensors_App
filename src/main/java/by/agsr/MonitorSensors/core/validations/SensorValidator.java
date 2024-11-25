package by.agsr.MonitorSensors.core.validations;

import by.agsr.MonitorSensors.core.dto.SensorRequestDTO;


public interface SensorValidator {

    void validateSensorRequest(SensorRequestDTO sensorRequestDTO);

    void validateExistingSensor(Long sensorId);
}
