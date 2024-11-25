package by.agsr.monitor.sensors.core.validations;

import by.agsr.monitor.sensors.core.api.dto.RangeDTO;
import by.agsr.monitor.sensors.core.api.dto.SensorRequestDTO;
import by.agsr.monitor.sensors.core.api.exceptions.SensorNotFoundException;
import by.agsr.monitor.sensors.core.api.exceptions.SensorRangeIncorrectException;
import by.agsr.monitor.sensors.core.api.exceptions.SensorRangeNotFoundException;
import by.agsr.monitor.sensors.core.api.exceptions.SensorTypeNotFoundException;
import by.agsr.monitor.sensors.core.api.exceptions.SensorUnitNotFoundException;
import by.agsr.monitor.sensors.core.models.Range;
import by.agsr.monitor.sensors.core.models.Sensor;
import by.agsr.monitor.sensors.core.models.SensorType;
import by.agsr.monitor.sensors.core.models.SensorUnit;
import by.agsr.monitor.sensors.core.repositories.RangeRepository;
import by.agsr.monitor.sensors.core.repositories.SensorRepository;
import by.agsr.monitor.sensors.core.repositories.SensorTypeRepository;
import by.agsr.monitor.sensors.core.repositories.SensorUnitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class SensorValidatorImplTest {

    @InjectMocks private SensorValidatorImpl sensorValidator;

    @Mock private SensorTypeRepository sensorTypeRepository;

    @Mock private SensorUnitRepository sensorUnitRepository;

    @Mock private SensorRepository sensorRepository;

    @Mock private RangeRepository rangeRepository;

    @Test
    public void shouldValidateNewSensorWithoutException() {
        var sensorRequestDTO = new SensorRequestDTO();
        sensorRequestDTO.setType("Temperature");
        sensorRequestDTO.setUnit("Celsius");
        RangeDTO range = new RangeDTO(10, 100);
        sensorRequestDTO.setRange(range);

        when(sensorTypeRepository.findByName("Temperature")).thenReturn(Optional.of(new SensorType()));
        when(sensorUnitRepository.findByName("Celsius")).thenReturn(Optional.of(new SensorUnit()));
        when(rangeRepository.findByRangeFromAndRangeTo(10, 100)).thenReturn(Optional.of(new Range()));
        sensorValidator.validateSensorRequest(sensorRequestDTO);

        verify(sensorTypeRepository, times(1)).findByName("Temperature");
        verify(sensorUnitRepository, times(1)).findByName("Celsius");
        verify(rangeRepository, times(1)).findByRangeFromAndRangeTo(10, 100);
    }

    @Test
    public void shouldThrowSensorTypeNotFoundException() {
        var sensorRequestDTO = new SensorRequestDTO();
        sensorRequestDTO.setType("InvalidType");
        when(sensorTypeRepository.findByName("InvalidType")).thenReturn(Optional.empty());

        assertThrows(SensorTypeNotFoundException.class, () -> sensorValidator.validateSensorRequest(sensorRequestDTO));
        verify(sensorTypeRepository, times(1)).findByName("InvalidType");
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
        when(sensorRepository.findById(validSensorId)).thenReturn(Optional.of(mock(Sensor.class)));
        sensorValidator.validateExistingSensor(validSensorId);

        verify(sensorRepository, times(1)).findById(validSensorId);
    }

    @Test
    public void shouldThrowExceptionWhenSensorDoesNotExist() {
        var invalidSensorId = 999L;
        when(sensorRepository.findById(invalidSensorId)).thenReturn(Optional.empty());
        assertThrows(SensorNotFoundException.class,
                () -> sensorValidator.validateExistingSensor(invalidSensorId));

        verify(sensorRepository, times(1)).findById(invalidSensorId);
    }
}