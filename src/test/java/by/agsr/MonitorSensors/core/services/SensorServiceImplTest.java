package by.agsr.MonitorSensors.core.services;

import by.agsr.MonitorSensors.core.dto.RangeDTO;
import by.agsr.MonitorSensors.core.dto.SensorRequestDTO;
import by.agsr.MonitorSensors.core.dto.SensorResponseDTO;
import by.agsr.MonitorSensors.core.dto.ValidationErrorDTO;
import by.agsr.MonitorSensors.core.models.Sensor;
import by.agsr.MonitorSensors.core.repositories.SensorRepository;
import by.agsr.MonitorSensors.core.validations.SensorValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
public class SensorServiceImplTest {

    @Mock
    private SensorRepository sensorRepository;

    @Mock
    private ConverterDTO converterDTO;

    @Mock
    private SensorValidator sensorValidator;

    @InjectMocks
    private SensorServiceImpl sensorService;

    @Test
    public void shouldCreateNewSuccessSensorResponse() {
        SensorRequestDTO sensorRequestDTO = new SensorRequestDTO();
        sensorRequestDTO.setName("Temperature Sensor");
        sensorRequestDTO.setDescription("Measures temperature");
        sensorRequestDTO.setLocation("Room 101");
        sensorRequestDTO.setModel("TS-100");
        sensorRequestDTO.setUnit("Celsius");
        sensorRequestDTO.setRange(new RangeDTO(0, 100));

        var sensor = new Sensor();
        sensor.setName(sensorRequestDTO.getName());

        when(sensorValidator.validateNewSensor(sensorRequestDTO)).thenReturn(List.of());
        when(converterDTO.convertToSensor(sensorRequestDTO)).thenReturn(sensor);
        when(sensorRepository.save(sensor)).thenReturn(sensor);
        when(converterDTO.convertToSensorResponseDTO(sensor)).thenReturn(new SensorResponseDTO());

        var sensorResponseDTO = sensorService.createSensor(sensorRequestDTO);

        assertNull(sensorResponseDTO.getErrors());
        verify(sensorRepository).save(sensor);
        verify(sensorValidator).validateNewSensor(sensorRequestDTO);
    }

    @Test
    public void shouldCreateErrorSensorResponse() {
        SensorRequestDTO sensorRequestDTO = new SensorRequestDTO();
        List<ValidationErrorDTO> validationErrors = List.of(new ValidationErrorDTO("name", "Name is required"));
        when(sensorValidator.validateNewSensor(sensorRequestDTO)).thenReturn(validationErrors);

        SensorResponseDTO sensorResponseDTO = sensorService.createSensor(sensorRequestDTO);

        assertEquals(1, sensorResponseDTO.getErrors().size());
        assertEquals("name", sensorResponseDTO.getErrors().get(0).getField());
        assertEquals("Name is required", sensorResponseDTO.getErrors().get(0).getMessage());
        verify(sensorRepository, never()).save(any(Sensor.class));
    }


    @Test
    public void shouldReturnsListOfSensors() {
        Sensor sensor = new Sensor();
        SensorResponseDTO sensorResponseDTO = new SensorResponseDTO();
        when(sensorRepository.findAll()).thenReturn(List.of(sensor));
        when(converterDTO.convertToSensorResponseDTO(sensor)).thenReturn(sensorResponseDTO);

        List<SensorResponseDTO> result = sensorService.getAllSensors();

        assertEquals(1, result.size());
        assertEquals(sensorResponseDTO, result.get(0));
        verify(sensorRepository).findAll();
        verify(converterDTO).convertToSensorResponseDTO(sensor);
    }

    @Test
    public void shouldReturnsEmptyList() {
        when(sensorRepository.findAll()).thenReturn(List.of());
        List<SensorResponseDTO> result = sensorService.getAllSensors();
        assertEquals(0, result.size());

        verify(sensorRepository).findAll();
        verify(converterDTO, never()).convertToSensorResponseDTO(any());
    }

    @Test
    public void shouldDeleteSensorWhenSensorExists() {
        Long sensorId = 1L;
        when(sensorValidator.isSensorExist(sensorId)).thenReturn(true);
        sensorService.deleteSensor(sensorId);

        verify(sensorValidator).isSensorExist(sensorId);
        verify(sensorRepository).deleteById(sensorId);
    }

    @Test
    public void testDeleteSensor_WhenSensorDoesNotExist() {
        Long sensorId = 2L;
        when(sensorValidator.isSensorExist(sensorId)).thenReturn(false);
        sensorService.deleteSensor(sensorId);

        verify(sensorValidator).isSensorExist(sensorId);
        verify(sensorRepository, never()).deleteById(sensorId);
    }
}
