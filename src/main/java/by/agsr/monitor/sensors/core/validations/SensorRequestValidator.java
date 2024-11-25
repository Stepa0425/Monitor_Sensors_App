package by.agsr.monitor.sensors.core.validations;

import by.agsr.monitor.sensors.core.api.dto.SensorRequestDTO;


public interface SensorRequestValidator {

    void validateSensorRequestOnCreate(SensorRequestDTO sensorRequestDTO);

    void validateSensorRequestOnDelete(Long sensorId);
}
