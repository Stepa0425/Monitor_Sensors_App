package by.agsr.MonitorSensors.validations;

import by.agsr.MonitorSensors.dto.ValidationErrorDTO;
import by.agsr.MonitorSensors.models.Sensor;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class SensorValidatorImpl implements SensorValidator {

    @Override
    public List<ValidationErrorDTO> validateNewSensor(Sensor sensor) {
        return null;
    }
}
