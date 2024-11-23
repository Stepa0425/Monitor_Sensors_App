package by.agsr.MonitorSensors.services;

import by.agsr.MonitorSensors.dto.SensorRequestDTO;
import by.agsr.MonitorSensors.dto.SensorResponseDTO;
import by.agsr.MonitorSensors.dto.ValidationErrorDTO;
import by.agsr.MonitorSensors.models.Sensor;
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
    private final ConverterDTO converterDTO;

    @Autowired
    private final SensorValidator sensorValidator;

    @Override
    public SensorResponseDTO createSensor(SensorRequestDTO sensorRequestDTO) {
        var errors = sensorValidator.validateNewSensor(sensorRequestDTO);
        return errors.isEmpty()
                ? buildSuccessSensorResponse(sensorRequestDTO)
                : buildErrorSensorResponse(errors);
    }

    private SensorResponseDTO buildErrorSensorResponse(List<ValidationErrorDTO> errors) {
        var sensorResponseDTO = new SensorResponseDTO();
        sensorResponseDTO.setErrors(errors);
        return sensorResponseDTO;
    }

    private SensorResponseDTO buildSuccessSensorResponse(SensorRequestDTO sensorRequestDTO) {
        Sensor sensor = converterDTO.convertToSensor(sensorRequestDTO);
        var savedSensor = sensorRepository.save(sensor);
        return converterDTO.convertToSensorResponseDTO(savedSensor);
    }
}