package by.agsr.MonitorSensors.core.validations;

import by.agsr.MonitorSensors.core.dto.RangeDTO;
import by.agsr.MonitorSensors.core.dto.SensorRequestDTO;
import by.agsr.MonitorSensors.core.dto.ValidationErrorDTO;
import by.agsr.MonitorSensors.core.models.Range;
import by.agsr.MonitorSensors.core.models.SensorType;
import by.agsr.MonitorSensors.core.models.SensorUnit;
import by.agsr.MonitorSensors.core.repositories.RangeRepository;
import by.agsr.MonitorSensors.core.repositories.SensorTypeRepository;
import by.agsr.MonitorSensors.core.repositories.SensorUnitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.anyString;

@ExtendWith(MockitoExtension.class)
public class SensorValidatorImplTest {

    @InjectMocks
    private SensorValidatorImpl sensorValidator;

    @Mock
    private SensorTypeRepository sensorTypeRepository;

    @Mock
    private SensorUnitRepository sensorUnitRepository;

    @Mock
    private RangeRepository rangeRepository;

    @Test
    public void shouldReturnNoErrorWhenSensorSuccess() {
        var sensorRequestDTO = new SensorRequestDTO();
        sensorRequestDTO.setType("Temperature");
        sensorRequestDTO.setUnit("°С");
        sensorRequestDTO.setRange(new RangeDTO(10,20));

        when(sensorTypeRepository.findByName("Temperature")).thenReturn(Optional.of(new SensorType()));
        when(sensorUnitRepository.findByName("°С")).thenReturn(Optional.of(new SensorUnit()));
        var rangeFrom = sensorRequestDTO.getRange().getRangeFrom();
        var rangeTo = sensorRequestDTO.getRange().getRangeTo();
        when(rangeRepository.findByRangeFromAndRangeTo(rangeFrom, rangeTo)).thenReturn(Optional.of(new Range()));

        List<ValidationErrorDTO> errors = sensorValidator.validateNewSensor(sensorRequestDTO);
        assertEquals(0, errors.size());
        verify(sensorTypeRepository).findByName("Temperature");
        verify(sensorUnitRepository).findByName("°С");
        verify(rangeRepository).findByRangeFromAndRangeTo(rangeFrom, rangeTo);
    }

    @Test
    public void shouldReturnErrorWhenSensorUnitDoesNotExist() {
        var sensorRequestDTO = new SensorRequestDTO();
        sensorRequestDTO.setUnit("NonExistentUnit");
        when(sensorUnitRepository.findByName("NonExistentUnit")).thenReturn(Optional.empty());

        List<ValidationErrorDTO> errors = sensorValidator.validateNewSensor(sensorRequestDTO);
        assertEquals(1, errors.size());
        assertEquals("unit", errors.get(0).getField());
        assertEquals("The unit: NonExistentUnit not exist.", errors.get(0).getMessage());
        verify(sensorUnitRepository).findByName("NonExistentUnit");
    }

    @Test
    public void shouldReturnNoErrorWhenUnitIsNull() {
        var sensorRequestDTO = new SensorRequestDTO();
        sensorRequestDTO.setUnit(null);

        List<ValidationErrorDTO> errors = sensorValidator.validateNewSensor(sensorRequestDTO);
        assertEquals(0, errors.size());
        verify(sensorTypeRepository, never()).findByName(anyString());
    }

    @Test
    public void shouldReturnErrorWhenSensorTypeDoesNotExist() {
        var sensorRequestDTO = new SensorRequestDTO();
        sensorRequestDTO.setType("NonExistentType");
        when(sensorTypeRepository.findByName("NonExistentType")).thenReturn(Optional.empty());

        List<ValidationErrorDTO> errors = sensorValidator.validateNewSensor(sensorRequestDTO);
        assertEquals(1, errors.size());
        assertEquals("type", errors.get(0).getField());
        assertEquals("The type: NonExistentType not exist.", errors.get(0).getMessage());
        verify(sensorTypeRepository).findByName("NonExistentType");
    }

    @Test
    public void shouldReturnNoErrorWhenTypeIsNull() {
        var sensorRequestDTO = new SensorRequestDTO();
        sensorRequestDTO.setType(null);

        List<ValidationErrorDTO> errors = sensorValidator.validateNewSensor(sensorRequestDTO);
        assertEquals(0, errors.size());
        verify(sensorTypeRepository, never()).findByName(anyString());
    }

    @Test
    public void shouldReturnErrorWhenRangeDoesNotExist(){
        var sensorRequestDTO = new SensorRequestDTO();
        var rangeFrom = 10;
        var rangeTo = 20;
        var range = new RangeDTO(rangeFrom, rangeTo);
        sensorRequestDTO.setRange(range);

        when(rangeRepository.findByRangeFromAndRangeTo(rangeFrom, rangeTo)).thenReturn(Optional.empty());
        List<ValidationErrorDTO> errors = sensorValidator.validateNewSensor(sensorRequestDTO);
        assertEquals(1, errors.size());
        assertEquals("range", errors.get(0).getField());
        assertEquals("The range: Range{10, 20} not exist.", errors.get(0).getMessage());
        verify(rangeRepository).findByRangeFromAndRangeTo(rangeFrom , rangeTo);
    }

    @Test
    public void shouldReturnNoErrorWhenRangeIsNull() {
        var sensorRequestDTO = new SensorRequestDTO();
        sensorRequestDTO.setRange(null);

        List<ValidationErrorDTO> errors = sensorValidator.validateNewSensor(sensorRequestDTO);
        assertEquals(0, errors.size());
        verify(rangeRepository, never()).findByRangeFromAndRangeTo(anyInt(), anyInt());
    }

    @Test
    public void shouldReturnErrorWhenRangeIsNotCorrect(){
        var sensorRequestDTO = new SensorRequestDTO();
        var rangeDTO = new RangeDTO(20, 10);
        sensorRequestDTO.setRange(rangeDTO);

        when(rangeRepository.findByRangeFromAndRangeTo(20, 10)).thenReturn(Optional.of(new Range()));
        List<ValidationErrorDTO> errors = sensorValidator.validateNewSensor(sensorRequestDTO);
        assertEquals(1, errors.size());
        assertEquals("range", errors.get(0).getField());
        assertEquals("The range: Range{20, 10} isn't correct.", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnNoErrorWhenRangeFromFieldIsNull(){
        var sensorRequestDTO = new SensorRequestDTO();
        sensorRequestDTO.setRange(new RangeDTO(null, 20));

        List<ValidationErrorDTO> errors = sensorValidator.validateNewSensor(sensorRequestDTO);
        assertEquals(0, errors.size());
        verify(rangeRepository, never()).findByRangeFromAndRangeTo(anyInt(), anyInt());
    }

    @Test
    public void shouldReturnNoErrorWhenRangeToFieldIsNull(){
        var sensorRequestDTO = new SensorRequestDTO();
        sensorRequestDTO.setRange(new RangeDTO( 20, null));

        List<ValidationErrorDTO> errors = sensorValidator.validateNewSensor(sensorRequestDTO);
        assertEquals(0, errors.size());
        verify(rangeRepository, never()).findByRangeFromAndRangeTo(anyInt(), anyInt());
    }
}