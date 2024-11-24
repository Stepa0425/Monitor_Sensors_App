package by.agsr.MonitorSensors.core.validations;

import by.agsr.MonitorSensors.core.dto.SensorRequestDTO;
import by.agsr.MonitorSensors.core.dto.ValidationErrorDTO;

import java.util.List;


public interface SensorValidator {

    List<ValidationErrorDTO> validateNewSensor(SensorRequestDTO sensorRequestDTO);

    boolean isSensorExist(Long sensorId);
}
