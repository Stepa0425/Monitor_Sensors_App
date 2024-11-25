package by.agsr.monitor.sensors.core.validations;


import by.agsr.monitor.sensors.core.api.exceptions.SensorUnitNotFoundException;
import by.agsr.monitor.sensors.core.models.SensorUnit;
import by.agsr.monitor.sensors.core.repositories.SensorUnitRepository;
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
public class SensorUnitExistValidatorTest {

    @InjectMocks
    private SensorUnitExistValidator sensorUnitExistValidator;

    @Mock
    private SensorUnitRepository sensorUnitRepository;

    @Test
    public void shouldDoNothingWhenUnitFound() {
        var name = "Sensor unit";
        when(sensorUnitRepository.findByName(name)).thenReturn(Optional.of(mock(SensorUnit.class)));
        sensorUnitExistValidator.validate(name);
        verify(sensorUnitRepository).findByName(name);
    }

    @Test
    public void shouldDoNothingWhenTypeNull() {
        String name = null;
        sensorUnitExistValidator.validate(name);
        verifyNoInteractions(sensorUnitRepository);
    }

    @Test
    public void shouldDoNothingWhenUnitBlank() {
        String name = "   ";
        sensorUnitExistValidator.validate(name);
        verifyNoInteractions(sensorUnitRepository);
    }

    @Test
    public void shouldThrowException() {
        var name = "NonExistUnit";
        when(sensorUnitRepository.findByName(name)).thenReturn(Optional.empty());
        var exception = assertThrows(SensorUnitNotFoundException.class,
                () -> sensorUnitExistValidator.validate(name));
        assertEquals("Sensor unit with name: NonExistUnit not found.", exception.getMessage());
    }
}