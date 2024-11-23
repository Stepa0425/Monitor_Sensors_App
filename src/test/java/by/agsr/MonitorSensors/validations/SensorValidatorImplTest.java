package by.agsr.MonitorSensors.validations;

import by.agsr.MonitorSensors.dto.SensorRequestDTO;
import by.agsr.MonitorSensors.dto.ValidationErrorDTO;
import by.agsr.MonitorSensors.models.SensorType;
import by.agsr.MonitorSensors.repositories.SensorTypeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.anyString;

@ExtendWith(MockitoExtension.class)
public class SensorValidatorImplTest {

    @InjectMocks
    private SensorValidatorImpl sensorValidator;

    @Mock
    private SensorTypeRepository sensorTypeRepository;

    @Test
    void shouldReturnNoErrorsWhenSensorTypeExists() {
        SensorRequestDTO sensorRequestDTO = new SensorRequestDTO();
        sensorRequestDTO.setType("Temperature");
        when(sensorTypeRepository.findByName("Temperature")).thenReturn(Optional.of(new SensorType()));

        List<ValidationErrorDTO> errors = sensorValidator.validateNewSensor(sensorRequestDTO);
        assertEquals(0, errors.size());
        verify(sensorTypeRepository).findByName("Temperature");
    }

    @Test
    void shouldReturnErrorWhenSensorTypeDoesNotExist() {
        SensorRequestDTO sensorRequestDTO = new SensorRequestDTO();
        sensorRequestDTO.setType("NonExistentType");
        when(sensorTypeRepository.findByName("NonExistentType")).thenReturn(Optional.empty());

        List<ValidationErrorDTO> errors = sensorValidator.validateNewSensor(sensorRequestDTO);
        assertEquals(1, errors.size());
        assertEquals("type", errors.get(0).getField());
        assertEquals("The type: NonExistentType not exist.", errors.get(0).getMessage());
        verify(sensorTypeRepository).findByName("NonExistentType");
    }

    @Test
    void shouldReturnNoErrorsWhenTypeIsNull() {
        SensorRequestDTO sensorRequestDTO = new SensorRequestDTO();
        sensorRequestDTO.setType(null);

        List<ValidationErrorDTO> errors = sensorValidator.validateNewSensor(sensorRequestDTO);
        assertEquals(0, errors.size());
        verify(sensorTypeRepository, never()).findByName(anyString());
    }
}