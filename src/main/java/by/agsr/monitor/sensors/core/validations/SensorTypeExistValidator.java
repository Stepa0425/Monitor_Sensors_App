package by.agsr.monitor.sensors.core.validations;

import by.agsr.monitor.sensors.core.api.exceptions.SensorTypeNotFoundException;
import by.agsr.monitor.sensors.core.repositories.SensorTypeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class SensorTypeExistValidator {

    @Autowired
    private final SensorTypeRepository sensorTypeRepository;

    public void validateExistSensorType(String type) {
        if (type != null && !type.isBlank()) {
            sensorTypeRepository.findByName(type)
                    .orElseThrow(() -> new SensorTypeNotFoundException(type));
        }
    }
}
