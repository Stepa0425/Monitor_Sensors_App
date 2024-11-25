package by.agsr.monitor.sensors.core.validations;

import by.agsr.monitor.sensors.core.api.exceptions.SensorNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SensorDeleteValidatorTest {

    @InjectMocks
    private SensorDeleteValidator sensorDeleteValidator;

    @Mock
    private SensorExistValidator sensorExistValidator;

    @Test
    public void shouldSuccessValidateExistSensor() {
        var validSensorId = 1L;
        sensorDeleteValidator.validate(validSensorId);
        verify(sensorExistValidator, times(1)).validate(validSensorId);
    }

    @Test
    public void shouldThrowExceptionWhenSensorDoesNotExist() {
        var invalidSensorId = 999L;
        doThrow(new SensorNotFoundException(invalidSensorId))
                .when(sensorExistValidator).validate(invalidSensorId);
        var exception = assertThrows(SensorNotFoundException.class,
                () -> sensorDeleteValidator.validate(invalidSensorId));
        assertEquals("Sensor with id: 999 not found.", exception.getMessage());
    }
}