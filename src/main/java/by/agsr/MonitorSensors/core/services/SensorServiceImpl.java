package by.agsr.MonitorSensors.core.services;

import by.agsr.MonitorSensors.core.dto.SensorRequestDTO;
import by.agsr.MonitorSensors.core.dto.SensorResponseDTO;
import by.agsr.MonitorSensors.core.dto.ValidationErrorDTO;
import by.agsr.MonitorSensors.core.repositories.SensorRepository;
import by.agsr.MonitorSensors.core.validations.SensorValidator;
import by.agsr.MonitorSensors.core.models.Sensor;

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

    @Override
    public List<SensorResponseDTO> getAllSensors(){
        return sensorRepository.findAll().stream()
                .map(converterDTO::convertToSensorResponseDTO)
                .toList();
    }
}