package by.agsr.MonitorSensors.services;

import by.agsr.MonitorSensors.dto.RangeDTO;
import by.agsr.MonitorSensors.dto.SensorRequestDTO;
import by.agsr.MonitorSensors.dto.SensorResponseDTO;
import by.agsr.MonitorSensors.models.Range;
import by.agsr.MonitorSensors.models.Sensor;
import by.agsr.MonitorSensors.models.SensorType;
import by.agsr.MonitorSensors.models.SensorUnit;
import by.agsr.MonitorSensors.repositories.RangeRepository;
import by.agsr.MonitorSensors.repositories.SensorTypeRepository;
import by.agsr.MonitorSensors.repositories.SensorUnitRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ConverterDTO {

    @Autowired
    private final SensorTypeRepository sensorTypeRepository;

    @Autowired
    private final SensorUnitRepository sensorUnitRepository;

    @Autowired
    private final RangeRepository rangeRepository;

    public Sensor convertToSensor(SensorRequestDTO sensorRequestDTO){
        Sensor sensor = new Sensor();
        Integer rangeFrom = sensorRequestDTO.getRange().getRangeFrom();
        Integer rangeTo = sensorRequestDTO.getRange().getRangeTo();

        SensorType sensorType = sensorTypeRepository.findByName(sensorRequestDTO.getName()).get();
        SensorUnit sensorUnit = sensorUnitRepository.findByName(sensorRequestDTO.getUnit()).get();
        Range range = rangeRepository.findByRangeFromAndRangeTo(rangeFrom, rangeTo).get();

        sensor.setName(sensorRequestDTO.getName());
        sensor.setDescription(sensorRequestDTO.getDescription());
        sensor.setLocation(sensorRequestDTO.getLocation());
        sensor.setModel(sensorRequestDTO.getModel());
        sensor.setType(sensorType);
        sensor.setUnit(sensorUnit);
        sensor.setRange(range);

        return sensor;
    }

    public SensorResponseDTO convertToSensorResponseDTO(Sensor sensor){
        SensorResponseDTO sensorResponseDTO = new SensorResponseDTO();
        Integer rangeFrom = sensor.getRange().getRangeFrom();
        Integer rangeTo = sensor.getRange().getRangeTo();
        RangeDTO rangeDTO = new RangeDTO(rangeFrom, rangeTo);

        sensorResponseDTO.setName(sensor.getName());
        sensorResponseDTO.setModel(sensor.getModel());
        sensorResponseDTO.setDescription(sensor.getDescription());
        sensorResponseDTO.setLocation(sensor.getLocation());
        sensorResponseDTO.setType(sensor.getType().getName());
        sensorResponseDTO.setUnit(sensor.getUnit().getName());
        sensorResponseDTO.setRangeDTO(rangeDTO);

        return sensorResponseDTO;
    }
}
