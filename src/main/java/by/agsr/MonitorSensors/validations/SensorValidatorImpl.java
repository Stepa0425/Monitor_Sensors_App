package by.agsr.MonitorSensors.validations;

import by.agsr.MonitorSensors.dto.RangeDTO;
import by.agsr.MonitorSensors.dto.SensorRequestDTO;
import by.agsr.MonitorSensors.dto.ValidationErrorDTO;
import by.agsr.MonitorSensors.repositories.RangeRepository;
import by.agsr.MonitorSensors.repositories.SensorTypeRepository;
import by.agsr.MonitorSensors.repositories.SensorUnitRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class SensorValidatorImpl implements SensorValidator {

    @Autowired
    private final SensorTypeRepository sensorTypeRepository;

    @Autowired
    private final SensorUnitRepository sensorUnitRepository;

    @Autowired
    private final RangeRepository rangeRepository;

    @Override
    public List<ValidationErrorDTO> validateNewSensor(SensorRequestDTO sensorRequestDTO) {
        String type = sensorRequestDTO.getType();
        String unit = sensorRequestDTO.getUnit();
        RangeDTO range = sensorRequestDTO.getRange();

        List<Optional<ValidationErrorDTO>> errors = List.of(
                validateExistingSensorType(type),
                validateExistingSensorUnit(unit),
                validateCorrectRange(range),
                validateExistingRange(range)
        );

        return errors.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    private Optional<ValidationErrorDTO> validateExistingSensorType(String type) {
        return type != null && sensorTypeRepository.findByName(type).isEmpty()
                ? Optional.of(new ValidationErrorDTO("type", "The type: " + type + " not exist."))
                : Optional.empty();
    }

    private Optional<ValidationErrorDTO> validateExistingSensorUnit(String unit) {
        return unit != null && sensorUnitRepository.findByName(unit).isEmpty()
                ? Optional.of(new ValidationErrorDTO("unit", "The unit: " + unit + " not exist."))
                : Optional.empty();
    }

    private Optional<ValidationErrorDTO> validateExistingRange(RangeDTO rangeDTO) {
        return rangeDTO != null && rangeDTO.getRangeFrom() != null && rangeDTO.getRangeTo() != null
                && rangeRepository.findByRangeFromAndRangeTo(rangeDTO.getRangeFrom(), rangeDTO.getRangeTo()).isEmpty()
                ? Optional.of(new ValidationErrorDTO("range", "The range: " + rangeDTO + " not exist."))
                : Optional.empty();
    }

    private Optional<ValidationErrorDTO> validateCorrectRange(RangeDTO rangeDTO) {
        return rangeDTO != null
                && rangeDTO.getRangeTo() != null
                && rangeDTO.getRangeFrom() != null
                && rangeDTO.getRangeFrom().compareTo(rangeDTO.getRangeTo()) >= 0
                ? Optional.of(new ValidationErrorDTO("range", "The range: " + rangeDTO + " isn't correct."))
                : Optional.empty();
    }

}
