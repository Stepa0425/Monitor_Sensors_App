package by.agsr.monitor.sensors.core.validations;

import by.agsr.monitor.sensors.core.api.dto.SensorRequestDTO;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class SensorRequestValidatorImpl implements SensorRequestValidator {

    @Autowired
    private final SensorCreateValidator sensorCreateValidator;

    @Autowired
    private final SensorExistValidator sensorExistValidator;

    @Override
    public void validateSensorRequestOnCreation(SensorRequestDTO sensorRequestDTO) {
        sensorCreateValidator.validate(sensorRequestDTO);
    }

    @Override
    public void validateExistingSensor(Long sensorId) {
        sensorExistValidator.validate(sensorId);
    }
}
