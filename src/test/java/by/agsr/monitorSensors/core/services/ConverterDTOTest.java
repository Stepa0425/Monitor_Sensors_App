package by.agsr.monitorSensors.core.services;

import by.agsr.monitorSensors.core.api.dto.RangeDTO;
import by.agsr.monitorSensors.core.api.dto.SensorRequestDTO;
import by.agsr.monitorSensors.core.models.Range;
import by.agsr.monitorSensors.core.models.Sensor;
import by.agsr.monitorSensors.core.models.SensorType;
import by.agsr.monitorSensors.core.models.SensorUnit;
import by.agsr.monitorSensors.core.repositories.RangeRepository;
import by.agsr.monitorSensors.core.repositories.SensorTypeRepository;
import by.agsr.monitorSensors.core.repositories.SensorUnitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ConverterDTOTest {

    @Mock
    private SensorTypeRepository sensorTypeRepository;

    @Mock
    private SensorUnitRepository sensorUnitRepository;

    @Mock
    private RangeRepository rangeRepository;

    @InjectMocks
    private ConverterDTO converterDTO;

    @Test
    public void convertToSensor() {
        var sensorRequestDTO = getSensorRequestDTO();
        var sensorType = getSensorType();
        var sensorUnit = getSensorUnit();
        var range = getRange();

        sensorRequestDTO.setType(sensorType.getName());
        sensorRequestDTO.setUnit(sensorUnit.getName());
        sensorRequestDTO.setRange(new RangeDTO(getRange().getRangeFrom(), getRange().getRangeTo()));

        when(sensorTypeRepository.findByName("Temperature Sensor")).thenReturn(Optional.of(sensorType));
        when(sensorUnitRepository.findByName("Celsius")).thenReturn(Optional.of(sensorUnit));
        when(rangeRepository.findByRangeFromAndRangeTo(0, 100)).thenReturn(Optional.of(range));
        
        var sensor = converterDTO.convertToSensor(sensorRequestDTO);

        assertEquals("Temperature Sensor", sensor.getName());
        assertEquals("Measures temperature", sensor.getDescription());
        assertEquals("Room 101", sensor.getLocation());
        assertEquals("TS-100", sensor.getModel());
        assertEquals(sensorType, sensor.getType());
        assertEquals(sensorUnit, sensor.getUnit());
        assertEquals(range, sensor.getRange());
    }

    @Test
    public void convertToSensorResponseDTO() {
        var sensor = getSensor();
        var sensorType = getSensorType();
        var sensorUnit = getSensorUnit();
        var range = getRange();

        sensor.setType(sensorType);
        sensor.setUnit(sensorUnit);
        sensor.setRange(range);

        var sensorResponseDTO = converterDTO.convertToSensorResponseDTO(sensor);

        assertEquals("Temperature Sensor", sensorResponseDTO.getName());
        assertEquals("Measures temperature", sensorResponseDTO.getDescription());
        assertEquals("Room 101", sensorResponseDTO.getLocation());
        assertEquals("TS-100", sensorResponseDTO.getModel());
        assertEquals("Temperature Sensor", sensorResponseDTO.getType());
        assertEquals("Celsius", sensorResponseDTO.getUnit());
        assertEquals(range.getRangeFrom(), sensorResponseDTO.getRange().getRangeFrom());
        assertEquals(range.getRangeTo(), sensorResponseDTO.getRange().getRangeTo());
    }

    private static Sensor getSensor() {
        var sensor = new Sensor();
        sensor.setName("Temperature Sensor");
        sensor.setDescription("Measures temperature");
        sensor.setLocation("Room 101");
        sensor.setModel("TS-100");

        return sensor;
    }

    private static SensorRequestDTO getSensorRequestDTO(){
        SensorRequestDTO sensorRequestDTO = new SensorRequestDTO();
        sensorRequestDTO.setName("Temperature Sensor");
        sensorRequestDTO.setDescription("Measures temperature");
        sensorRequestDTO.setLocation("Room 101");
        sensorRequestDTO.setModel("TS-100");
        sensorRequestDTO.setUnit("Celsius");
        sensorRequestDTO.setRange(new RangeDTO(0, 100));

        return  sensorRequestDTO;
    }

    private static SensorUnit getSensorUnit(){
        var sensorUnit = new SensorUnit();
        sensorUnit.setName("Celsius");
        return sensorUnit;
    }

    private static Range getRange(){
        var range = new Range();
        range.setRangeFrom(0);
        range.setRangeTo(100);
        return range;
    }

    private static SensorType getSensorType(){
        var sensorType = new SensorType();
        sensorType.setName("Temperature Sensor");
        return sensorType;
    }
}