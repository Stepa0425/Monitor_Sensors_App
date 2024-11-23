package by.agsr.MonitorSensors.validations;

import by.agsr.MonitorSensors.dto.SensorRequestDTO;
import by.agsr.MonitorSensors.dto.ValidationErrorDTO;

import java.util.List;


public interface SensorValidator {

    List<ValidationErrorDTO> validateNewSensor(SensorRequestDTO sensorRequestDTO);
}
