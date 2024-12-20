package by.agsr.monitor.sensors.core.validations.sensorFieldValidators;

import by.agsr.monitor.sensors.core.api.dto.SensorRequestDTO;
import by.agsr.monitor.sensors.core.api.exceptions.SensorTypeNotFoundException;
import by.agsr.monitor.sensors.core.repositories.SensorTypeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class SensorTypeExistValidator implements SensorFieldValidator {

    private final SensorTypeRepository sensorTypeRepository;

    public void validateField(SensorRequestDTO sensorRequestDTO) {
        String type = sensorRequestDTO.getType();
        if (type != null && !type.isBlank()) {
            sensorTypeRepository.findByName(type)
                    .orElseThrow(() -> new SensorTypeNotFoundException(type));
        }
    }
}
