package by.agsr.monitor.sensors.core.validations;

import by.agsr.monitor.sensors.core.api.dto.SensorRequestDTO;


public interface SensorValidator {

    void validateSensorRequest(SensorRequestDTO sensorRequestDTO);

    void validateExistingSensor(Long sensorId);
}
