package by.agsr.MonitorSensors.core.validations;

import by.agsr.MonitorSensors.core.dto.RangeDTO;
import by.agsr.MonitorSensors.core.dto.SensorRequestDTO;
import by.agsr.MonitorSensors.core.exceptions.SensorNotFoundException;
import by.agsr.MonitorSensors.core.exceptions.SensorRangeNotFoundException;
import by.agsr.MonitorSensors.core.exceptions.SensorTypeNotFoundException;
import by.agsr.MonitorSensors.core.exceptions.SensorUnitNotFoundException;
import by.agsr.MonitorSensors.core.models.Range;
import by.agsr.MonitorSensors.core.models.Sensor;
import by.agsr.MonitorSensors.core.models.SensorType;
import by.agsr.MonitorSensors.core.models.SensorUnit;
import by.agsr.MonitorSensors.core.repositories.RangeRepository;
import by.agsr.MonitorSensors.core.repositories.SensorRepository;
import by.agsr.MonitorSensors.core.repositories.SensorTypeRepository;
import by.agsr.MonitorSensors.core.repositories.SensorUnitRepository;
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
        sensorValidator.validateNewSensor(sensorRequestDTO);

        verify(sensorTypeRepository, times(1)).findByName("Temperature");
        verify(sensorUnitRepository, times(1)).findByName("Celsius");
        verify(rangeRepository, times(1)).findByRangeFromAndRangeTo(10, 100);
    }

    @Test
    public void shouldThrowSensorTypeNotFoundException() {
        var sensorRequestDTO = new SensorRequestDTO();
        sensorRequestDTO.setType("InvalidType");
        when(sensorTypeRepository.findByName("InvalidType")).thenReturn(Optional.empty());

        assertThrows(SensorTypeNotFoundException.class, () -> sensorValidator.validateNewSensor(sensorRequestDTO));
        verify(sensorTypeRepository, times(1)).findByName("InvalidType");
    }

    @Test
    public void shouldThrowSensorUnitNotFoundException() {
        var sensorRequestDTO = new SensorRequestDTO();
        sensorRequestDTO.setUnit("InvalidUnit");
        when(sensorUnitRepository.findByName("InvalidUnit")).thenReturn(Optional.empty());

        assertThrows(SensorUnitNotFoundException.class, () -> sensorValidator.validateNewSensor(sensorRequestDTO));
        verify(sensorUnitRepository, times(1)).findByName("InvalidUnit");
    }

    @Test
    public void shouldThrowRangeNotFoundException() {
        var sensorRequestDTO = new SensorRequestDTO();
        var range = new RangeDTO(10, 100);
        sensorRequestDTO.setRange(range);
        when(rangeRepository.findByRangeFromAndRangeTo(10, 100)).thenReturn(Optional.empty());

        assertThrows(SensorRangeNotFoundException.class,
                () -> sensorValidator.validateNewSensor(sensorRequestDTO));
        verify(rangeRepository, times(1)).findByRangeFromAndRangeTo(10, 100);
    }

    @Test
    public void shouldNotValidateRangeNull() {
        var sensorRequestDTO = new SensorRequestDTO();
        sensorRequestDTO.setRange(null);
        sensorValidator.validateNewSensor(sensorRequestDTO);
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