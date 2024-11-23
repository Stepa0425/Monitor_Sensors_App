package by.agsr.MonitorSensors.services;

import by.agsr.MonitorSensors.dto.SensorRequestDTO;
import by.agsr.MonitorSensors.dto.SensorResponseDTO;
import by.agsr.MonitorSensors.dto.ValidationErrorDTO;
import by.agsr.MonitorSensors.repositories.SensorRepository;
import by.agsr.MonitorSensors.validations.SensorValidator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class SensorServiceImpl implements SensorService {

    @Autowired
    private final SensorRepository sensorRepository;

    @Autowired
    private final SensorValidator sensorValidator;

    @Override
    public SensorResponseDTO createSensor(SensorRequestDTO sensorRequestDTO) {
        List<ValidationErrorDTO> errors = sensorValidator.validateNewSensor(sensorRequestDTO);

        return null;
    }
}
