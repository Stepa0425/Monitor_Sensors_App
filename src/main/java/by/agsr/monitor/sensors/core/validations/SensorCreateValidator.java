package by.agsr.monitor.sensors.core.validations;

import by.agsr.monitor.sensors.core.api.dto.SensorRequestDTO;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class SensorCreateValidator {

    @Autowired
    private final SensorUnitExistValidator sensorUnitExistValidator;

    @Autowired
    private final SensorRangeExistValidator sensorRangeExistValidator;

    @Autowired
    private final SensorTypeExistValidator sensorTypeExistValidator;

    @Autowired
    private final SensorRangeCorrectValidator sensorRangeCorrectValidator;


    public void validate(SensorRequestDTO sensorRequestDTO) {
        sensorTypeExistValidator.validateField(sensorRequestDTO);
        sensorRangeExistValidator.validateField(sensorRequestDTO);
        sensorRangeCorrectValidator.validateField(sensorRequestDTO);
        sensorUnitExistValidator.validateField(sensorRequestDTO);
    }
}
