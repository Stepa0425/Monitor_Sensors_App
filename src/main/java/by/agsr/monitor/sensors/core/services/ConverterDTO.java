package by.agsr.monitor.sensors.core.services;

import by.agsr.monitor.sensors.core.api.dto.RangeDTO;
import by.agsr.monitor.sensors.core.api.dto.SensorRequestDTO;
import by.agsr.monitor.sensors.core.api.dto.SensorResponseDTO;
import by.agsr.monitor.sensors.core.models.Range;
import by.agsr.monitor.sensors.core.models.SensorUnit;
import by.agsr.monitor.sensors.core.models.Sensor;
import by.agsr.monitor.sensors.core.models.SensorType;
import by.agsr.monitor.sensors.core.repositories.RangeRepository;
import by.agsr.monitor.sensors.core.repositories.SensorTypeRepository;
import by.agsr.monitor.sensors.core.repositories.SensorUnitRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class ConverterDTO {

    private final SensorTypeRepository sensorTypeRepository;

    private final SensorUnitRepository sensorUnitRepository;

    private final RangeRepository rangeRepository;

    Sensor convertToSensor(SensorRequestDTO sensorRequestDTO) {
        Sensor sensor = new Sensor();
        Integer rangeFrom = sensorRequestDTO.getRange().getRangeFrom();
        Integer rangeTo = sensorRequestDTO.getRange().getRangeTo();

        SensorType sensorType = sensorTypeRepository.findByName(sensorRequestDTO.getType()).get();
        Range range = rangeRepository.findByRangeFromAndRangeTo(rangeFrom, rangeTo).get();

        if (sensorRequestDTO.getUnit() != null && !sensorRequestDTO.getUnit().isBlank()) {
            SensorUnit sensorUnit = sensorUnitRepository.findByName(sensorRequestDTO.getUnit()).get();
            sensor.setUnit(sensorUnit);
        }

        sensor.setName(sensorRequestDTO.getName());
        sensor.setDescription(sensorRequestDTO.getDescription());
        sensor.setLocation(sensorRequestDTO.getLocation());
        sensor.setModel(sensorRequestDTO.getModel());
        sensor.setType(sensorType);
        sensor.setRange(range);

        return sensor;
    }

    SensorResponseDTO convertToSensorResponseDTO(Sensor sensor) {
        SensorResponseDTO sensorResponseDTO = new SensorResponseDTO();
        Integer rangeFrom = sensor.getRange().getRangeFrom();
        Integer rangeTo = sensor.getRange().getRangeTo();
        RangeDTO rangeDTO = new RangeDTO(rangeFrom, rangeTo);

        sensorResponseDTO.setName(sensor.getName());
        sensorResponseDTO.setModel(sensor.getModel());
        sensorResponseDTO.setDescription(sensor.getDescription());
        sensorResponseDTO.setLocation(sensor.getLocation());
        sensorResponseDTO.setType(sensor.getType().getName());

        if (sensor.getUnit() != null) {
            sensorResponseDTO.setUnit(sensor.getUnit().getName());
        }
        sensorResponseDTO.setRange(rangeDTO);

        return sensorResponseDTO;
    }
}
