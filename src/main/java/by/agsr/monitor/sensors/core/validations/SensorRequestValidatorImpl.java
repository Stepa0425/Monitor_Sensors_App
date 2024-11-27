package by.agsr.monitor.sensors.core.validations;

import by.agsr.monitor.sensors.core.api.dto.SensorRequestDTO;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class SensorRequestValidatorImpl implements SensorRequestValidator {

    private final SensorCreateValidator sensorCreateValidator;

    private final SensorDeleteValidator sensorDeleteValidator;

    private final SensorUpdateValidator sensorUpdateValidator;

    @Override
    public void validateSensorRequestOnCreate(SensorRequestDTO sensorRequestDTO) {
        sensorCreateValidator.validate(sensorRequestDTO);
    }

    @Override
    public void validateSensorRequestOnDelete(Long sensorId) {
        sensorDeleteValidator.validate(sensorId);
    }

    @Override
    public void validateSensorRequestOnUpdate(Long sensorId, SensorRequestDTO sensorRequestDTO) {
        sensorUpdateValidator.validate(sensorId, sensorRequestDTO);
    }
}
