package by.agsr.monitor.sensors.core.validations;

import by.agsr.monitor.sensors.core.api.dto.RangeDTO;
import by.agsr.monitor.sensors.core.api.dto.SensorRequestDTO;
import by.agsr.monitor.sensors.core.api.exceptions.SensorRangeIncorrectException;
import by.agsr.monitor.sensors.core.api.exceptions.SensorRangeNotFoundException;
import by.agsr.monitor.sensors.core.api.exceptions.SensorUnitNotFoundException;
import by.agsr.monitor.sensors.core.repositories.RangeRepository;
import by.agsr.monitor.sensors.core.repositories.SensorUnitRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class SensorRequestValidatorImpl implements SensorRequestValidator {

    @Autowired
    private final SensorUnitRepository sensorUnitRepository;

    @Autowired
    private final RangeRepository rangeRepository;

    @Autowired
    private final SensorExistValidator sensorExistValidator;

    @Autowired
    private final SensorTypeExistValidator sensorTypeExistValidator;



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
        if (unit != null && !unit.isBlank()) {
            sensorUnitRepository.findByName(unit)
                    .orElseThrow(() -> new SensorUnitNotFoundException(unit));
        }
    }

    private void validateExistRange(RangeDTO range) {
        if (range != null && range.getRangeFrom() != null && range.getRangeTo() != null) {
            rangeRepository.findByRangeFromAndRangeTo(range.getRangeFrom(), range.getRangeTo())
                    .orElseThrow(() -> new SensorRangeNotFoundException(range.getRangeFrom(), range.getRangeTo()));
        }
    }

    private void validateCorrectRange(RangeDTO range) {
        if (range != null
                && range.getRangeFrom() != null
                && range.getRangeTo() != null
                && range.getRangeFrom().compareTo(range.getRangeTo()) >= 0) {
            throw new SensorRangeIncorrectException(range.getRangeFrom(), range.getRangeTo());
        }
    }

    @Override
    public void validateExistingSensor(Long sensorId) {
        sensorExistValidator.validateExistingSensor(sensorId);
    }
}
