package by.agsr.monitor.sensors.core.validations;

import by.agsr.monitor.sensors.core.api.exceptions.SensorTypeNotFoundException;
import by.agsr.monitor.sensors.core.models.SensorType;
import by.agsr.monitor.sensors.core.repositories.SensorTypeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SensorTypeExistValidatorTest {

    @InjectMocks
    private SensorTypeExistValidator sensorTypeExistValidator;

    @Mock
    private SensorTypeRepository sensorTypeRepository;

    @Test
    public void shouldReturnSensorTypeSuccess() {
        var name = "Sensor type";
        when(sensorTypeRepository.findByName(name)).thenReturn(Optional.of(mock(SensorType.class)));
        sensorTypeExistValidator.validateExistSensorType(name);
        verify(sensorTypeRepository).findByName(name);
    }

    @Test
    public void shouldDoNothingWhenTypeNull() {
        String name = null;
        sensorTypeExistValidator.validateExistSensorType(name);
        verifyNoInteractions(sensorTypeRepository);
    }

    @Test
    public void shouldDoNothingWhenTypeBlank() {
        String name = "   ";
        sensorTypeExistValidator.validateExistSensorType(name);
        verifyNoInteractions(sensorTypeRepository);
    }

    @Test
    public void shouldThrowException() {
        var name = "NonExistType";
        when(sensorTypeRepository.findByName(name)).thenReturn(Optional.empty());
        var exception = assertThrows(SensorTypeNotFoundException.class,
                () -> sensorTypeExistValidator.validateExistSensorType(name));
        assertEquals("Sensor type with name: NonExistType not found.", exception.getMessage());
    }
}
