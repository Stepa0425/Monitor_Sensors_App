package by.agsr.MonitorSensors.core.validations;

import by.agsr.MonitorSensors.core.dto.RangeDTO;
import by.agsr.MonitorSensors.core.dto.SensorRequestDTO;
import by.agsr.MonitorSensors.core.exceptions.*;
import by.agsr.MonitorSensors.core.repositories.RangeRepository;
import by.agsr.MonitorSensors.core.repositories.SensorRepository;
import by.agsr.MonitorSensors.core.repositories.SensorTypeRepository;
import by.agsr.MonitorSensors.core.repositories.SensorUnitRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class SensorValidatorImpl implements SensorValidator {

    @Autowired
    private final SensorTypeRepository sensorTypeRepository;

    @Autowired
    private final SensorUnitRepository sensorUnitRepository;

    @Autowired
    private final RangeRepository rangeRepository;

    @Autowired
    private final SensorRepository sensorRepository;

    @Override
    public void validateNewSensor(SensorRequestDTO sensorRequestDTO) {
        String type = sensorRequestDTO.getType();
        String unit = sensorRequestDTO.getUnit();
        RangeDTO range = sensorRequestDTO.getRange();

        validateExistSensorType(type);
        validateExistRange(range);
        validateCorrectRange(range);
        validateExistSensorUnit(unit);
    }

    private void validateExistSensorType(String type) {
        if (type != null && !type.isBlank()) {
            sensorTypeRepository.findByName(type)
                    .orElseThrow(() -> new SensorTypeNotFoundException(type));
        }
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

    private void validateCorrectRange(RangeDTO range){
        if(range != null
                && range.getRangeFrom() != null
                && range.getRangeTo() != null
                && range.getRangeFrom().compareTo(range.getRangeTo()) >= 0){
            throw new SensorRangeIncorrectException(range.getRangeFrom(), range.getRangeTo());
        }
    }

    @Override
    public void validateExistingSensor(Long sensorId) {
        sensorRepository.findById(sensorId)
                .orElseThrow(() -> new SensorNotFoundException(sensorId));
    }
}
