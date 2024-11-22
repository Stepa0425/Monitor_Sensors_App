package by.agsr.MonitorSensors.validations;

import by.agsr.MonitorSensors.exceptions.SensorTypeNotFoundException;
import by.agsr.MonitorSensors.models.Sensor;
import by.agsr.MonitorSensors.models.SensorType;
import by.agsr.MonitorSensors.repositories.SensorTypeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class SensorValidatorImpl implements SensorValidator {

    @Autowired
    private final SensorTypeRepository sensorTypeRepository;


    @Override
    public void validateNewSensor(Sensor sensor) {
        SensorType sensorType = sensor.getType();

        validateExistingSensorType(sensorType);
    }

    private void validateExistingSensorType(SensorType sensorType) {
        if (sensorType == null) {
            throw new IllegalArgumentException("Sensor type cannot be null");
        }

        sensorTypeRepository.findByName(sensorType.getName())
                .orElseThrow(() -> new SensorTypeNotFoundException(sensorType.getName()));
    }
}
