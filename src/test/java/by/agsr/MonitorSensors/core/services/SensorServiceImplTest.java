package by.agsr.MonitorSensors.core.services;

import by.agsr.MonitorSensors.core.dto.SensorRequestDTO;
import by.agsr.MonitorSensors.core.dto.SensorResponseDTO;
import by.agsr.MonitorSensors.core.exceptions.SensorNotFoundException;
import by.agsr.MonitorSensors.core.exceptions.SensorTypeNotFoundException;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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
    public void shouldCreateSuccessSensor() {
        var sensorRequestDTO = new SensorRequestDTO();
        var sensor = new Sensor();
        var savedSensor = new Sensor();
        var responseDTO = new SensorResponseDTO();

        when(converterDTO.convertToSensor(sensorRequestDTO)).thenReturn(sensor);
        when(sensorRepository.save(sensor)).thenReturn(savedSensor);
        when(converterDTO.convertToSensorResponseDTO(savedSensor)).thenReturn(responseDTO);
        SensorResponseDTO result = sensorService.createSensor(sensorRequestDTO);

        verify(sensorValidator, times(1)).validateNewSensor(sensorRequestDTO);
        verify(converterDTO, times(1)).convertToSensor(sensorRequestDTO);
        verify(sensorRepository, times(1)).save(sensor);
        verify(converterDTO, times(1)).convertToSensorResponseDTO(savedSensor);

        assertNotNull(result);
        assertEquals(responseDTO, result);
    }

    @Test
    public void shouldStopCreationWhenThrowException() {
        SensorRequestDTO sensorRequestDTO = new SensorRequestDTO();
        doThrow(new SensorTypeNotFoundException("Not exist sensor type")).when(sensorValidator).validateNewSensor(sensorRequestDTO);

        var exception = assertThrows(SensorTypeNotFoundException.class,
                () -> sensorService.createSensor(sensorRequestDTO));

        assertEquals("Sensor type with name: Not exist sensor type not found.", exception.getMessage());
        verify(sensorValidator, times(1)).validateNewSensor(sensorRequestDTO);
        verifyNoInteractions(converterDTO);
        verifyNoInteractions(sensorRepository);
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
    void deleteSensor_existingSensorId_noExceptionThrown() {
        var validSensorId = 1L;
        sensorService.deleteSensor(validSensorId);

        verify(sensorValidator, times(1)).validateExistingSensor(validSensorId);
        verify(sensorRepository, times(1)).deleteById(validSensorId);
    }

    @Test
    void deleteSensor_nonExistingSensorId_throwsSensorNotFoundException() {
        var invalidSensorId = 999L;
        doThrow(new SensorNotFoundException(invalidSensorId)).when(sensorValidator).validateExistingSensor(invalidSensorId);
        assertThrows(SensorNotFoundException.class,
                () -> sensorService.deleteSensor(invalidSensorId));

        verify(sensorValidator, times(1)).validateExistingSensor(invalidSensorId);
        verify(sensorRepository, never()).deleteById(any());
    }
}
