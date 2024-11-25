package by.agsr.monitor.sensors.core.validations;

import by.agsr.monitor.sensors.core.api.dto.RangeDTO;
import by.agsr.monitor.sensors.core.api.dto.SensorRequestDTO;
import by.agsr.monitor.sensors.core.api.exceptions.SensorNotFoundException;
import by.agsr.monitor.sensors.core.api.exceptions.SensorRangeIncorrectException;
import by.agsr.monitor.sensors.core.api.exceptions.SensorRangeNotFoundException;
import by.agsr.monitor.sensors.core.api.exceptions.SensorTypeNotFoundException;
import by.agsr.monitor.sensors.core.api.exceptions.SensorUnitNotFoundException;
import by.agsr.monitor.sensors.core.models.Range;
import by.agsr.monitor.sensors.core.models.SensorUnit;
import by.agsr.monitor.sensors.core.repositories.RangeRepository;
import by.agsr.monitor.sensors.core.repositories.SensorUnitRepository;
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
    private SensorUnitRepository sensorUnitRepository;

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
        sensorRequestDTO.setUnit("Celsius");
        RangeDTO range = new RangeDTO(10, 100);
        sensorRequestDTO.setRange(range);

        doNothing().when(sensorTypeExistValidator).validateExistSensorType(typeName);
        when(sensorUnitRepository.findByName("Celsius")).thenReturn(Optional.of(new SensorUnit()));
        when(rangeRepository.findByRangeFromAndRangeTo(10, 100)).thenReturn(Optional.of(new Range()));
        sensorValidator.validateSensorRequest(sensorRequestDTO);

        verify(sensorTypeExistValidator, times(1)).validateExistSensorType(typeName);
        verify(sensorUnitRepository, times(1)).findByName("Celsius");
        verify(rangeRepository, times(1)).findByRangeFromAndRangeTo(10, 100);
    }

    @Test
    public void shouldThrowSensorTypeNotFoundException() {
        var sensorRequestDTO = new SensorRequestDTO();
        var typeName = "InvalidType";
        sensorRequestDTO.setType(typeName);
        doThrow(new SensorTypeNotFoundException(typeName))
                .when(sensorTypeExistValidator).validateExistSensorType(typeName);

        var exception = assertThrows(SensorTypeNotFoundException.class,
                () -> sensorValidator.validateSensorRequest(sensorRequestDTO));
        assertEquals("Sensor type with name: InvalidType not found.", exception.getMessage());
    }

    @Test
    public void shouldThrowSensorUnitNotFoundException() {
        var sensorRequestDTO = new SensorRequestDTO();
        sensorRequestDTO.setUnit("InvalidUnit");
        when(sensorUnitRepository.findByName("InvalidUnit")).thenReturn(Optional.empty());

        assertThrows(SensorUnitNotFoundException.class, () -> sensorValidator.validateSensorRequest(sensorRequestDTO));
        verify(sensorUnitRepository, times(1)).findByName("InvalidUnit");
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