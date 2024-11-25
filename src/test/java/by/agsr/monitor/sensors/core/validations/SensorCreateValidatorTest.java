package by.agsr.monitor.sensors.core.validations;


import by.agsr.monitor.sensors.core.api.dto.RangeDTO;
import by.agsr.monitor.sensors.core.api.dto.SensorRequestDTO;
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
class SensorCreateValidatorTest {

    @InjectMocks
    private SensorCreateValidator sensorCreateValidator;

    @Mock
    private SensorUnitExistValidator sensorUnitExistValidator;

    @Mock
    private SensorRangeExistValidator sensorRangeExistValidator;

    @Mock
    private SensorTypeExistValidator sensorTypeExistValidator;

    @Mock
    private SensorRangeCorrectValidator sensorRangeCorrectValidator;

    @Test
    public void shouldValidateNewSensorWithoutException() {
        var sensorRequestDTO = new SensorRequestDTO();
        var typeName = "Temperature";
        var unitName = "Celsius";
        var range = new RangeDTO(10, 100);

        sensorRequestDTO.setType(typeName);
        sensorRequestDTO.setUnit(unitName);
        sensorRequestDTO.setRange(range);

        doNothing().when(sensorTypeExistValidator).validate(typeName);
        doNothing().when(sensorUnitExistValidator).validate(unitName);
        doNothing().when(sensorRangeExistValidator).validate(range);
        doNothing().when(sensorRangeCorrectValidator).validate(range);
        sensorCreateValidator.validate(sensorRequestDTO);

        verify(sensorTypeExistValidator, times(1)).validate(typeName);
        verify(sensorUnitExistValidator, times(1)).validate(unitName);
        verify(sensorRangeExistValidator, times(1)).validate(range);
        verify(sensorRangeCorrectValidator, times(1)).validate(range);
    }

    @Test
    public void shouldThrowSensorTypeNotFoundException() {
        var sensorRequestDTO = new SensorRequestDTO();
        var typeName = "NonExistType";
        sensorRequestDTO.setType(typeName);
        doThrow(new SensorTypeNotFoundException(typeName))
                .when(sensorTypeExistValidator).validate(typeName);

        var exception = assertThrows(SensorTypeNotFoundException.class,
                () -> sensorCreateValidator.validate(sensorRequestDTO));
        assertEquals("Sensor type with name: NonExistType not found.", exception.getMessage());
    }

    @Test
    public void shouldThrowSensorUnitNotFoundException() {
        var sensorRequestDTO = new SensorRequestDTO();
        var unitName = "NonExistUnit";
        sensorRequestDTO.setUnit(unitName);
        doThrow(new SensorUnitNotFoundException(unitName))
                .when(sensorUnitExistValidator).validate(unitName);

        var exception = assertThrows(SensorUnitNotFoundException.class,
                () -> sensorCreateValidator.validate(sensorRequestDTO));
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
                .when(sensorRangeExistValidator).validate(range);

        var exception = assertThrows(SensorRangeNotFoundException.class,
                () -> sensorCreateValidator.validate(sensorRequestDTO));
        assertEquals("The range {10, 100} not found.", exception.getMessage());
    }

    @Test
    public void shouldThrowRangeIncorrectException() {
        var sensorRequestDTO = new SensorRequestDTO();
        var from = 100;
        var to = 10;
        var range = new RangeDTO(from, to);
        sensorRequestDTO.setRange(range);
        doThrow(new SensorRangeIncorrectException(from, to)).when(sensorRangeCorrectValidator).validate(range);

        var exception = assertThrows(SensorRangeIncorrectException.class,
                () -> sensorCreateValidator.validate(sensorRequestDTO));
        assertEquals("The range {100, 10} not correct.", exception.getMessage());
    }
}