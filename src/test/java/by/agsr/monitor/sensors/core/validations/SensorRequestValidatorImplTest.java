package by.agsr.monitor.sensors.core.validations;

import by.agsr.monitor.sensors.core.api.dto.RangeDTO;
import by.agsr.monitor.sensors.core.api.dto.SensorRequestDTO;
import by.agsr.monitor.sensors.core.api.exceptions.SensorNotFoundException;
import by.agsr.monitor.sensors.core.api.exceptions.SensorRangeIncorrectException;
import by.agsr.monitor.sensors.core.api.exceptions.SensorRangeNotFoundException;
import by.agsr.monitor.sensors.core.api.exceptions.SensorTypeNotFoundException;
import by.agsr.monitor.sensors.core.api.exceptions.SensorUnitNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SensorRequestValidatorImplTest {

    @InjectMocks
    private SensorRequestValidatorImpl sensorValidator;

    @Mock
    private SensorUnitExistValidator sensorUnitExistValidator;

    @Mock
    private SensorRangeExistValidator sensorRangeExistValidator;

    @Mock
    private SensorExistValidator sensorExistValidator;

    @Mock
    private SensorTypeExistValidator sensorTypeExistValidator;

    @Test
    public void shouldValidateNewSensorWithoutException() {
        var sensorRequestDTO = new SensorRequestDTO();
        var typeName = "Temperature";
        var unitName = "Celsius";
        var range = new RangeDTO(10, 100);

        sensorRequestDTO.setType(typeName);
        sensorRequestDTO.setUnit(unitName);
        sensorRequestDTO.setRange(range);

        doNothing().when(sensorTypeExistValidator).validateExistSensorType(typeName);
        doNothing().when(sensorUnitExistValidator).validateExistSensorUnit(unitName);
        doNothing().when(sensorRangeExistValidator).validateExistRange(range);
        sensorValidator.validateSensorRequest(sensorRequestDTO);

        verify(sensorTypeExistValidator, times(1)).validateExistSensorType(typeName);
        verify(sensorUnitExistValidator, times(1)).validateExistSensorUnit(unitName);
        verify(sensorRangeExistValidator, times(1)).validateExistRange(range);
    }

    @Test
    public void shouldThrowSensorTypeNotFoundException() {
        var sensorRequestDTO = new SensorRequestDTO();
        var typeName = "NonExistType";
        sensorRequestDTO.setType(typeName);
        doThrow(new SensorTypeNotFoundException(typeName))
                .when(sensorTypeExistValidator).validateExistSensorType(typeName);

        var exception = assertThrows(SensorTypeNotFoundException.class,
                () -> sensorValidator.validateSensorRequest(sensorRequestDTO));
        assertEquals("Sensor type with name: NonExistType not found.", exception.getMessage());
    }

    @Test
    public void shouldThrowSensorUnitNotFoundException() {
        var sensorRequestDTO = new SensorRequestDTO();
        var unitName = "NonExistUnit";
        sensorRequestDTO.setUnit(unitName);
        doThrow(new SensorUnitNotFoundException(unitName))
                .when(sensorUnitExistValidator).validateExistSensorUnit(unitName);

        var exception = assertThrows(SensorUnitNotFoundException.class,
                () -> sensorValidator.validateSensorRequest(sensorRequestDTO));
        assertEquals("Sensor unit with name: NonExistUnit not found.", exception.getMessage());
    }

    @Test
    public void shouldThrowRangeNotFoundException() {
        var sensorRequestDTO = new SensorRequestDTO();
        var from = 10;
        var to = 100;
        var range = new RangeDTO(from, to);
        sensorRequestDTO.setRange(range);
        doThrow(new SensorRangeNotFoundException(from, to))
                .when(sensorRangeExistValidator).validateExistRange(range);

        var exception = assertThrows(SensorRangeNotFoundException.class,
                () -> sensorValidator.validateSensorRequest(sensorRequestDTO));
        assertEquals("The range {10, 100} not found.", exception.getMessage());
    }

    @Test
    public void shouldThrowRangeIncorrectException() {
        var sensorRequestDTO = new SensorRequestDTO();
        var range = new RangeDTO(100, 10);
        sensorRequestDTO.setRange(range);
        doNothing().when(sensorRangeExistValidator).validateExistRange(range);

        assertThrows(SensorRangeIncorrectException.class,
                () -> sensorValidator.validateSensorRequest(sensorRequestDTO));
    }

    @Test
    public void shouldSuccessValidateExistSensor() {
        var validSensorId = 1L;
        sensorValidator.validateExistingSensor(validSensorId);
        verify(sensorExistValidator, times(1)).validateExistingSensor(validSensorId);
    }

    @Test
    public void shouldThrowExceptionWhenSensorDoesNotExist() {
        var invalidSensorId = 999L;
        doThrow(new SensorNotFoundException(invalidSensorId))
                .when(sensorExistValidator).validateExistingSensor(invalidSensorId);
        var exception = assertThrows(SensorNotFoundException.class,
                () -> sensorValidator.validateExistingSensor(invalidSensorId));
        assertEquals("Sensor with id: 999 not found.", exception.getMessage());
    }
}