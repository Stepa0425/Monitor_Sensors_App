package by.agsr.monitor.sensors.core.validations;

import by.agsr.monitor.sensors.core.api.dto.RangeDTO;
import by.agsr.monitor.sensors.core.api.dto.SensorRequestDTO;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class SensorRequestValidatorImpl implements SensorRequestValidator {

    @Autowired
    private final SensorUnitExistValidator sensorUnitExistValidator;

    @Autowired
    private final SensorRangeExistValidator sensorRangeExistValidator;

    @Autowired
    private final SensorExistValidator sensorExistValidator;

    @Autowired
    private final SensorTypeExistValidator sensorTypeExistValidator;

    @Autowired
    private final SensorRangeCorrectValidator sensorRangeCorrectValidator;

    @Override
    public void validateSensorRequest(SensorRequestDTO sensorRequestDTO) {
        String type = sensorRequestDTO.getType();
        String unit = sensorRequestDTO.getUnit();
        RangeDTO range = sensorRequestDTO.getRange();

        validateExistSensorType(type);
        validateExistRange(range);
        validateCorrectRange(range);
        validateExistSensorUnit(unit);
    }

    private void validateExistSensorType(String type) {
        sensorTypeExistValidator.validateExistSensorType(type);
    }

    private void validateExistSensorUnit(String unit) {
        sensorUnitExistValidator.validateExistSensorUnit(unit);
    }

    private void validateExistRange(RangeDTO range) {
        sensorRangeExistValidator.validateExistRange(range);
    }

    private void validateCorrectRange(RangeDTO range) {
     sensorRangeCorrectValidator.validateCorrectRange(range);
    }

    @Override
    public void validateExistingSensor(Long sensorId) {
        sensorExistValidator.validateExistingSensor(sensorId);
    }
}
