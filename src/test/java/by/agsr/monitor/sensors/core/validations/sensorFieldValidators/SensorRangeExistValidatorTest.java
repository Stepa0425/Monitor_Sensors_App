package by.agsr.monitor.sensors.core.validations.sensorFieldValidators;


import by.agsr.monitor.sensors.core.api.dto.RangeDTO;
import by.agsr.monitor.sensors.core.api.dto.SensorRequestDTO;
import by.agsr.monitor.sensors.core.api.exceptions.SensorRangeNotFoundException;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SensorRangeExistValidatorTest {

    @InjectMocks
    private SensorRangeExistValidator sensorRangeExistValidator;

    @Mock
    private RangeRepository rangeRepository;

    private final SensorRequestDTO sensorRequestDTO = new SensorRequestDTO();

    @Test
    public void shouldDoNothingWhenRangeFound(){
        var from = 10;
        var to = 20;
        sensorRequestDTO.setRange(new RangeDTO(from, to));
        when(rangeRepository.findByRangeFromAndRangeTo(from, to)).thenReturn(Optional.of(mock(Range.class)));
        sensorRangeExistValidator.validateField(sensorRequestDTO);
        verify(rangeRepository).findByRangeFromAndRangeTo(from, to);
    }

    @Test
    public void shouldDoNothingWhenRangeNull(){
        sensorRangeExistValidator.validateField(sensorRequestDTO);
        verifyNoInteractions(rangeRepository);
    }

    @Test
    public void shouldDoNothingWhenRangeFromFieldNull(){
        sensorRequestDTO.setRange(new RangeDTO(null, 20));
        sensorRangeExistValidator.validateField(sensorRequestDTO);
        verifyNoInteractions(rangeRepository);
    }

    @Test
    public void shouldDoNothingWhenRangeToFieldNull(){
        sensorRequestDTO.setRange(new RangeDTO(10, null));
        sensorRangeExistValidator.validateField(sensorRequestDTO);
        verifyNoInteractions(rangeRepository);
    }

    @Test
    public void shouldThrowExceptionNotFoundRange(){
        var from = 10;
        var to = 20;
        sensorRequestDTO.setRange(new RangeDTO(from, to));
        when(rangeRepository.findByRangeFromAndRangeTo(from, to)).thenReturn(Optional.empty());
        var exception = assertThrows(SensorRangeNotFoundException.class,
                () -> sensorRangeExistValidator.validateField(sensorRequestDTO));
        assertEquals("The range {10, 20} not found.", exception.getMessage());
    }
}