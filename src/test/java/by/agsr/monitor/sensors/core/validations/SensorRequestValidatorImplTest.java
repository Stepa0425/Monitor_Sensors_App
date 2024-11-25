package by.agsr.monitor.sensors.core.validations;

import by.agsr.monitor.sensors.core.api.dto.SensorRequestDTO;
import by.agsr.monitor.sensors.core.api.exceptions.SensorNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SensorRequestValidatorImplTest {

    @InjectMocks
    private SensorRequestValidatorImpl sensorValidator;

    @Mock
    private SensorCreateValidator sensorCreateValidator;

    @Mock
    private SensorExistValidator sensorExistValidator;

    @Test
    public void shouldValidateNewSensorWithoutException() {
        var sensor = mock(SensorRequestDTO.class);
        sensorValidator.validateSensorRequestOnCreation(sensor);
        verify(sensorCreateValidator, times(1)).validate(sensor);
    }

    @Test
    void shouldThrowExceptionWhenSensorCreateValidatorFails() {
        var sensorBadRequestDTO = mock(SensorRequestDTO.class);
        doThrow(new RuntimeException("Validation failed"))
                .when(sensorCreateValidator).validate(sensorBadRequestDTO);

        assertThrows(RuntimeException.class,
                () -> sensorValidator.validateSensorRequestOnCreation(sensorBadRequestDTO));
        verify(sensorCreateValidator, times(1)).validate(sensorBadRequestDTO);
    }


    @Test
    public void shouldSuccessValidateExistSensor() {
        var validSensorId = 1L;
        sensorValidator.validateExistingSensor(validSensorId);
        verify(sensorExistValidator, times(1)).validate(validSensorId);
    }

    @Test
    public void shouldThrowExceptionWhenSensorDoesNotExist() {
        var invalidSensorId = 999L;
        doThrow(new SensorNotFoundException(invalidSensorId))
                .when(sensorExistValidator).validate(invalidSensorId);
        var exception = assertThrows(SensorNotFoundException.class,
                () -> sensorValidator.validateExistingSensor(invalidSensorId));
        assertEquals("Sensor with id: 999 not found.", exception.getMessage());
    }
}