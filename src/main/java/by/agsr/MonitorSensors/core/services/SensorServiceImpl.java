package by.agsr.MonitorSensors.core.services;

import by.agsr.MonitorSensors.core.dto.SensorRequestDTO;
import by.agsr.MonitorSensors.core.dto.SensorResponseDTO;
import by.agsr.MonitorSensors.core.models.Sensor;
import by.agsr.MonitorSensors.core.repositories.SensorRepository;
import by.agsr.MonitorSensors.core.validations.SensorValidator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
        sensorValidator.validateNewSensor(sensorRequestDTO);

        Sensor sensor = converterDTO.convertToSensor(sensorRequestDTO);
        Sensor savedSensor = sensorRepository.save(sensor);
        return converterDTO.convertToSensorResponseDTO(savedSensor);
    }

    @Override
    public List<SensorResponseDTO> getAllSensors() {
        return sensorRepository.findAll().stream()
                .map(converterDTO::convertToSensorResponseDTO)
                .toList();
    }

    @Override
    public void deleteSensor(Long sensorId) {
        sensorValidator.validateExistingSensor(sensorId);
        sensorRepository.deleteById(sensorId);
    }
}