package by.agsr.monitor.sensors.core.services;

import by.agsr.monitor.sensors.core.api.dto.SensorRequestDTO;
import by.agsr.monitor.sensors.core.api.dto.SensorResponseDTO;
import by.agsr.monitor.sensors.core.models.Sensor;
import by.agsr.monitor.sensors.core.repositories.SensorRepository;
import by.agsr.monitor.sensors.core.validations.SensorRequestValidator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class SensorServiceImpl implements SensorService {

    private final SensorRepository sensorRepository;

    private final ConverterDTO converterDTO;

    private final SensorRequestValidator sensorValidator;

    @Override
    public SensorResponseDTO createSensor(SensorRequestDTO sensorRequestDTO) {
        sensorValidator.validateSensorRequestOnCreate(sensorRequestDTO);
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
        sensorValidator.validateSensorRequestOnDelete(sensorId);
        sensorRepository.deleteById(sensorId);
    }

    @Override
    public SensorResponseDTO updateSensor(Long sensorId, SensorRequestDTO sensorRequestDTO) {
        sensorValidator.validateSensorRequestOnUpdate(sensorId, sensorRequestDTO);
        Sensor sensor = sensorRepository.findById(sensorId).get();
        Sensor updatedSensor = converterDTO.convertToSensor(sensorRequestDTO);

        sensor.setName(updatedSensor.getName());
        sensor.setModel(updatedSensor.getModel());
        sensor.setDescription(updatedSensor.getDescription());
        sensor.setLocation(updatedSensor.getLocation());
        sensor.setUnit(updatedSensor.getUnit());
        sensor.setType(updatedSensor.getType());
        sensor.setRange(updatedSensor.getRange());

        Sensor savedSensor = sensorRepository.save(sensor);
        return converterDTO.convertToSensorResponseDTO(savedSensor);
    }

    @Override
    public List<SensorResponseDTO> getSensorsByName(String name) {
        return isCorrectSearchValue(name)
                ? findSensorsByName(name)
                : getAllSensors();
    }

    @Override
    public List<SensorResponseDTO> getSensorsByModel(String model) {
        return isCorrectSearchValue(model)
                ? findSensorsByModel(model)
                : getAllSensors();
    }

    private boolean isCorrectSearchValue(String value) {
        return value != null && !value.isBlank();
    }

    private List<SensorResponseDTO> findSensorsByName(String name) {
        List<Sensor> foundSensors = sensorRepository.findByNameContaining(name);
        return foundSensors.stream()
                .map(converterDTO::convertToSensorResponseDTO)
                .toList();
    }

    private List<SensorResponseDTO> findSensorsByModel(String model) {
        List<Sensor> foundSensors = sensorRepository.findByModelContaining(model);
        return foundSensors.stream()
                .map(converterDTO::convertToSensorResponseDTO)
                .toList();
    }
}