package by.agsr.monitor.sensors.core.validations;

import by.agsr.monitor.sensors.core.api.dto.SensorRequestDTO;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class SensorUpdateValidator {

    @Autowired
    private final SensorUnitExistValidator sensorUnitExistValidator;

    @Autowired
    private final SensorRangeExistValidator sensorRangeExistValidator;

    @Autowired
    private final SensorTypeExistValidator sensorTypeExistValidator;

    @Autowired
    private final SensorRangeCorrectValidator sensorRangeCorrectValidator;

    @Autowired
    private final SensorExistValidator sensorExistValidator;

    public void validate(Long sensorId, SensorRequestDTO sensorRequestDTO){
        String type = sensorRequestDTO.getType();
        String unit = sensorRequestDTO.getUnit();

        sensorExistValidator.validate(sensorId);
        sensorTypeExistValidator.validate(type);
        sensorRangeExistValidator.validateField(sensorRequestDTO);
        sensorRangeCorrectValidator.validateField(sensorRequestDTO);
        sensorUnitExistValidator.validate(unit);
    }

}
