package by.agsr.monitor.sensors.core.validations;

import by.agsr.monitor.sensors.core.api.dto.SensorRequestDTO;
import by.agsr.monitor.sensors.core.validations.sensorFieldValidators.SensorFieldValidator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class SensorCreateValidator {

    private final List<SensorFieldValidator> sensorFieldValidators;

    public void validate(SensorRequestDTO sensorRequestDTO) {
       sensorFieldValidators.forEach(
               sensorFieldValidator -> sensorFieldValidator.validateField(sensorRequestDTO)
       );
    }
}
