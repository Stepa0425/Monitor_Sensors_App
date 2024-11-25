package by.agsr.monitor.sensors.core.validations;

import by.agsr.monitor.sensors.core.api.exceptions.SensorUnitNotFoundException;
import by.agsr.monitor.sensors.core.repositories.SensorUnitRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class SensorUnitExistValidator {

    @Autowired
    private final SensorUnitRepository sensorUnitRepository;

    public void validateExistSensorUnit(String unit) {
        if (unit != null && !unit.isBlank()) {
            sensorUnitRepository.findByName(unit)
                    .orElseThrow(() -> new SensorUnitNotFoundException(unit));
        }
    }
}
