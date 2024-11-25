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
    private final SensorDeleteValidator sensorDeleteValidator;

    @Override
    public void validateSensorRequestOnCreate(SensorRequestDTO sensorRequestDTO) {
        sensorCreateValidator.validate(sensorRequestDTO);
    }

    @Override
    public void validateSensorRequestOnDelete(Long sensorId) {
        sensorDeleteValidator.validate(sensorId);
    }
}
