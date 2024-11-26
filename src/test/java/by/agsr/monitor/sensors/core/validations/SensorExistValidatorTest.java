package by.agsr.monitor.sensors.core.validations;

import by.agsr.monitor.sensors.core.api.exceptions.SensorNotFoundException;
import by.agsr.monitor.sensors.core.models.Sensor;
import by.agsr.monitor.sensors.core.repositories.SensorRepository;
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
public class SensorExistValidatorTest {

    @InjectMocks
    private SensorExistValidator sensorValidator;

    @Mock
    private SensorRepository sensorRepository;

    @Test
    public void shouldDoNothingWhenSensorFound(){
        var id = 1L;
        when(sensorRepository.findById(id)).thenReturn(Optional.of(mock(Sensor.class)));
        sensorValidator.validate(id);
        verify(sensorRepository).findById(id);
    }

    @Test
    public void shouldThrowException(){
        var id = 1L;
        when(sensorRepository.findById(id)).thenReturn(Optional.empty());
        var exception = assertThrows(SensorNotFoundException.class,
                ()-> sensorValidator.validate(id));
        assertEquals("Sensor with id: 1 not found.", exception.getMessage());
    }

    @Test
    public void shouldDoNothingWhenSensorIdNull(){
        Long id = null;
        sensorValidator.validate(id);
        verifyNoInteractions(sensorRepository);
    }
}
