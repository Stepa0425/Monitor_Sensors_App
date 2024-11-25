package by.agsr.monitor.sensors.core.validations;

import by.agsr.monitor.sensors.core.api.dto.SensorRequestDTO;

public interface SensorFieldValidator {
    void validate(SensorRequestDTO sensorRequestDTO);
}
