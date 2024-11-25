package by.agsr.monitorSensors.core.validations;

import by.agsr.monitorSensors.core.api.dto.SensorRequestDTO;


public interface SensorValidator {

    void validateSensorRequest(SensorRequestDTO sensorRequestDTO);

    void validateExistingSensor(Long sensorId);
}
