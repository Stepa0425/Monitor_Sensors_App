package by.agsr.MonitorSensors.core.validations;

import by.agsr.MonitorSensors.core.dto.SensorRequestDTO;


public interface SensorValidator {

    void validateNewSensor(SensorRequestDTO sensorRequestDTO);

    void validateExistingSensor(Long sensorId);
}
