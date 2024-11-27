package by.agsr.monitor.sensors.core.validations;

import by.agsr.monitor.sensors.core.api.exceptions.SensorNotFoundException;
import by.agsr.monitor.sensors.core.repositories.SensorRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class SensorExistValidator {

    private final SensorRepository sensorRepository;

    public void validate(Long sensorId) {
        if (sensorId != null) {
            sensorRepository.findById(sensorId)
                    .orElseThrow(() -> new SensorNotFoundException(sensorId));
        }
    }
}
