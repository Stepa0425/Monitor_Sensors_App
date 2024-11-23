package by.agsr.MonitorSensors.validations;

import by.agsr.MonitorSensors.dto.SensorRequestDTO;
import by.agsr.MonitorSensors.dto.ValidationErrorDTO;
import by.agsr.MonitorSensors.repositories.SensorTypeRepository;
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

    @Override
    public List<ValidationErrorDTO> validateNewSensor(SensorRequestDTO sensorRequestDTO) {
        String type = sensorRequestDTO.getType();

        List<Optional<ValidationErrorDTO>> errors = List.of(isExistSensorType(type));

        return errors.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    private Optional<ValidationErrorDTO> isExistSensorType(String type) {
        return type != null && sensorTypeRepository.findByName(type).isEmpty()
                ? Optional.of(new ValidationErrorDTO("type", "The type: " + type + " not exist."))
                : Optional.empty();
    }

}
