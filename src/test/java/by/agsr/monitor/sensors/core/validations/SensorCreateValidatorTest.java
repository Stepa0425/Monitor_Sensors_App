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

    private final SensorRequestDTO sensorRequestDTO = new SensorRequestDTO();

    @Test
    public void shouldValidateNewSensorWithoutException() {
        var typeName = "Temperature";
        var unitName = "Celsius";
        var range = new RangeDTO(10, 100);

        sensorRequestDTO.setType(typeName);
        sensorRequestDTO.setUnit(unitName);
        sensorRequestDTO.setRange(range);

        doNothing().when(sensorTypeExistValidator).validateField(sensorRequestDTO);
        doNothing().when(sensorUnitExistValidator).validateField(sensorRequestDTO);
        doNothing().when(sensorRangeExistValidator).validateField(sensorRequestDTO);
        doNothing().when(sensorRangeCorrectValidator).validateField(sensorRequestDTO);
        sensorCreateValidator.validate(sensorRequestDTO);

        verify(sensorTypeExistValidator, times(1)).validateField(sensorRequestDTO);
        verify(sensorUnitExistValidator, times(1)).validateField(sensorRequestDTO);
        verify(sensorRangeExistValidator, times(1)).validateField(sensorRequestDTO);
        verify(sensorRangeCorrectValidator, times(1)).validateField(sensorRequestDTO);
    }

    @Test
    public void shouldThrowSensorTypeNotFoundException() {
        var typeName = "NonExistType";
        sensorRequestDTO.setType(typeName);
        doThrow(new SensorTypeNotFoundException(typeName))
                .when(sensorTypeExistValidator).validateField(sensorRequestDTO);

        var exception = assertThrows(SensorTypeNotFoundException.class,
                () -> sensorCreateValidator.validate(sensorRequestDTO));
        assertEquals("Sensor type with name: NonExistType not found.", exception.getMessage());
    }

    @Test
    public void shouldThrowSensorUnitNotFoundException() {
        var unitName = "NonExistUnit";
        sensorRequestDTO.setUnit(unitName);
        doThrow(new SensorUnitNotFoundException(unitName))
                .when(sensorUnitExistValidator).validateField(sensorRequestDTO);

        var exception = assertThrows(SensorUnitNotFoundException.class,
                () -> sensorCreateValidator.validate(sensorRequestDTO));
        assertEquals("Sensor unit with name: NonExistUnit not found.", exception.getMessage());
    }

    @Test
    public void shouldThrowRangeNotFoundException() {
        var from = 10;
        var to = 100;
        var range = new RangeDTO(from, to);
        sensorRequestDTO.setRange(range);
        doThrow(new SensorRangeNotFoundException(from, to))
                .when(sensorRangeExistValidator).validateField(sensorRequestDTO);

        var exception = assertThrows(SensorRangeNotFoundException.class,
                () -> sensorCreateValidator.validate(sensorRequestDTO));
        assertEquals("The range {10, 100} not found.", exception.getMessage());
    }

    @Test
    public void shouldThrowRangeIncorrectException() {
        var from = 100;
        var to = 10;
        var range = new RangeDTO(from, to);
        sensorRequestDTO.setRange(range);
        doThrow(new SensorRangeIncorrectException(from, to)).when(sensorRangeCorrectValidator).validateField(sensorRequestDTO);

        var exception = assertThrows(SensorRangeIncorrectException.class,
                () -> sensorCreateValidator.validate(sensorRequestDTO));
        assertEquals("The range {100, 10} not correct.", exception.getMessage());
    }
}