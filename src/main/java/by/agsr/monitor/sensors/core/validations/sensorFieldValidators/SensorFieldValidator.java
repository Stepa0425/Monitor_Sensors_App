package by.agsr.monitor.sensors.core.validations.sensorFieldValidators;

import by.agsr.monitor.sensors.core.api.dto.SensorRequestDTO;

public interface SensorFieldValidator {
    void validateField(SensorRequestDTO sensorRequestDTO);
}
