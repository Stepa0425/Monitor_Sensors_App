package by.agsr.monitor.sensors.core.validations;

import by.agsr.monitor.sensors.core.api.dto.SensorRequestDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    private SensorDeleteValidator sensorDeleteValidator;


    @Test
    public void shouldValidateNewSensorWithoutException() {
        var sensor = mock(SensorRequestDTO.class);
        sensorValidator.validateSensorRequestOnCreate(sensor);
        verify(sensorCreateValidator, times(1)).validate(sensor);
    }

    @Test
    void shouldThrowExceptionWhenSensorCreateValidatorFails() {
        var sensorBadRequestDTO = mock(SensorRequestDTO.class);
        doThrow(new RuntimeException("Validation failed"))
                .when(sensorCreateValidator).validate(sensorBadRequestDTO);

        assertThrows(RuntimeException.class,
                () -> sensorValidator.validateSensorRequestOnCreate(sensorBadRequestDTO));
        verify(sensorCreateValidator, times(1)).validate(sensorBadRequestDTO);
    }

    @Test
    void shouldValidateSensorRequestOnDeleteSuccessfully() {
        var validSensorId = 1L;
        sensorValidator.validateSensorRequestOnDelete(validSensorId);
        verify(sensorDeleteValidator, times(1)).validate(validSensorId);
    }

    @Test
    void shouldThrowExceptionWhenSensorDeleteValidationFails() {
        var invalidSensorId = 999L;
        doThrow(new RuntimeException("Sensor not found"))
                .when(sensorDeleteValidator).validate(invalidSensorId);

        assertThrows(RuntimeException.class,
                () -> sensorValidator.validateSensorRequestOnDelete(invalidSensorId));
        verify(sensorDeleteValidator, times(1)).validate(invalidSensorId);
    }
}