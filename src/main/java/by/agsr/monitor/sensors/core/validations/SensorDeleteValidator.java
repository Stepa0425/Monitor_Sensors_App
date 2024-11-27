package by.agsr.monitor.sensors.core.validations;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class SensorDeleteValidator {

    private final SensorExistValidator sensorExistValidator;

    public void validate(Long sensorId){
        sensorExistValidator.validate(sensorId);
    }
}
