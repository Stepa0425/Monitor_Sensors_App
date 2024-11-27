package by.agsr.monitor.sensors.core.validations;

import by.agsr.monitor.sensors.core.api.dto.SensorRequestDTO;
import by.agsr.monitor.sensors.core.validations.sensorFieldValidators.SensorFieldValidator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class SensorUpdateValidator {

    private final List<SensorFieldValidator> sensorFieldValidators;

    private final SensorExistValidator sensorExistValidator;

    public void validate(Long sensorId, SensorRequestDTO sensorRequestDTO){
        sensorExistValidator.validate(sensorId);
        sensorFieldValidators.forEach(
                sensorFieldValidator -> sensorFieldValidator.validateField(sensorRequestDTO)
        );
    }

}
