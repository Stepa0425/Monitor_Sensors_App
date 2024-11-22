package by.agsr.MonitorSensors.services;

import by.agsr.MonitorSensors.models.Sensor;
import by.agsr.MonitorSensors.repositories.SensorRepository;
import by.agsr.MonitorSensors.validations.SensorValidator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class SensorServiceImpl implements SensorService {

    @Autowired
    private final SensorRepository sensorRepository;

    @Autowired
    private final SensorValidator sensorValidator;

    @Override
    public Sensor createSensor(Sensor sensor) {
        sensorValidator.validateNewSensor(sensor);
        return sensorRepository.save(sensor);
    }
}
