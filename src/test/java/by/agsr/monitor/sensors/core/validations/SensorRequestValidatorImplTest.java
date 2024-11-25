package by.agsr.monitor.sensors.core.validations;

import by.agsr.monitor.sensors.core.api.dto.RangeDTO;
import by.agsr.monitor.sensors.core.api.dto.SensorRequestDTO;
import by.agsr.monitor.sensors.core.api.exceptions.SensorNotFoundException;
import by.agsr.monitor.sensors.core.api.exceptions.SensorRangeIncorrectException;
import by.agsr.monitor.sensors.core.api.exceptions.SensorRangeNotFoundException;
import by.agsr.monitor.sensors.core.api.exceptions.SensorTypeNotFoundException;
import by.agsr.monitor.sensors.core.api.exceptions.SensorUnitNotFoundException;
import by.agsr.monitor.sensors.core.models.Range;
import by.agsr.monitor.sensors.core.repositories.RangeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SensorRequestValidatorImplTest {

    @InjectMocks
    private SensorRequestValidatorImpl sensorValidator;

    @Mock
    private SensorUnitExistValidator sensorUnitExistValidator;

    @Mock
    private RangeRepository rangeRepository;

    @Mock
    private SensorExistValidator sensorExistValidator;

    @Mock
    private SensorTypeExistValidator sensorTypeExistValidator;

    @Test
    public void shouldValidateNewSensorWithoutException() {
        var sensorRequestDTO = new SensorRequestDTO();
        var typeName = "Temperature";
        sensorRequestDTO.setType(typeName);
        var unitName = "Celsius";
        sensorRequestDTO.setUnit(unitName);
        RangeDTO range = new RangeDTO(10, 100);
        sensorRequestDTO.setRange(range);

        doNothing().when(sensorTypeExistValidator).validateExistSensorType(typeName);
        doNothing().when(sensorUnitExistValidator).validateExistSensorUnit(unitName);
        when(rangeRepository.findByRangeFromAndRangeTo(10, 100)).thenReturn(Optional.of(new Range()));
        sensorValidator.validateSensorRequest(sensorRequestDTO);

        verify(sensorTypeExistValidator, times(1)).validateExistSensorType(typeName);
        verify(sensorUnitExistValidator, times(1)).validateExistSensorUnit(unitName);
        verify(rangeRepository, times(1)).findByRangeFromAndRangeTo(10, 100);
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
        var range = new RangeDTO(10, 100);
        sensorRequestDTO.setRange(range);
        when(rangeRepository.findByRangeFromAndRangeTo(10, 100)).thenReturn(Optional.empty());

        assertThrows(SensorRangeNotFoundException.class,
                () -> sensorValidator.validateSensorRequest(sensorRequestDTO));
        verify(rangeRepository, times(1)).findByRangeFromAndRangeTo(10, 100);
    }

    @Test
    public void shouldThrowRangeIncorrectException() {
        var sensorRequestDTO = new SensorRequestDTO();
        var range = new RangeDTO(100, 10);
        sensorRequestDTO.setRange(range);
        when(rangeRepository.findByRangeFromAndRangeTo(100, 10)).thenReturn(Optional.of(mock(Range.class)));

        assertThrows(SensorRangeIncorrectException.class,
                () -> sensorValidator.validateSensorRequest(sensorRequestDTO));
    }

    @Test
    public void shouldNotValidateRangeNull() {
        var sensorRequestDTO = new SensorRequestDTO();
        sensorRequestDTO.setRange(null);
        sensorValidator.validateSensorRequest(sensorRequestDTO);
        verifyNoInteractions(rangeRepository);
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