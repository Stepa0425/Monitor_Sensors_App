package by.agsr.monitor.sensors.core.validations.sensorFieldValidators;

import by.agsr.monitor.sensors.core.api.dto.SensorRequestDTO;
import by.agsr.monitor.sensors.core.api.exceptions.SensorUnitNotFoundException;
import by.agsr.monitor.sensors.core.repositories.SensorUnitRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class SensorUnitExistValidator implements SensorFieldValidator {

    private final SensorUnitRepository sensorUnitRepository;

    public void validateField(SensorRequestDTO sensorRequestDTO) {
        var unit = sensorRequestDTO.getUnit();
        if (unit != null && !unit.isBlank()) {
            sensorUnitRepository.findByName(unit)
                    .orElseThrow(() -> new SensorUnitNotFoundException(unit));
        }
    }
}
