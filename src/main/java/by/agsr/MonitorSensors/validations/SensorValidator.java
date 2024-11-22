package by.agsr.MonitorSensors.validations;

import by.agsr.MonitorSensors.dto.ValidationErrorDTO;
import by.agsr.MonitorSensors.models.Sensor;

import java.util.List;


public interface SensorValidator {

    List<ValidationErrorDTO> validateNewSensor(Sensor sensor);
}
