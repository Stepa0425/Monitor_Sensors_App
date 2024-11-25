package by.agsr.monitor.sensors.core.validations;


import by.agsr.monitor.sensors.core.api.dto.RangeDTO;
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

    @Test
    public void shouldDoNothingWhenRangeFound(){
        var from = 10;
        var to = 20;
        when(rangeRepository.findByRangeFromAndRangeTo(from, to)).thenReturn(Optional.of(mock(Range.class)));
        sensorRangeExistValidator.validate(new RangeDTO(from, to));
        verify(rangeRepository).findByRangeFromAndRangeTo(from, to);
    }

    @Test
    public void shouldDoNothingWhenRangeNull(){
        RangeDTO range = null;
        sensorRangeExistValidator.validate(range);
        verifyNoInteractions(rangeRepository);
    }

    @Test
    public void shouldDoNothingWhenRangeFromFieldNull(){
        var range = new RangeDTO(null, 20);
        sensorRangeExistValidator.validate(range);
        verifyNoInteractions(rangeRepository);
    }

    @Test
    public void shouldDoNothingWhenRangeToFieldNull(){
        var range = new RangeDTO(10, null);
        sensorRangeExistValidator.validate(range);
        verifyNoInteractions(rangeRepository);
    }

    @Test
    public void shouldThrowExceptionNotFoundRange(){
        var from = 10;
        var to = 20;
        when(rangeRepository.findByRangeFromAndRangeTo(from, to)).thenReturn(Optional.empty());
        var exception = assertThrows(SensorRangeNotFoundException.class,
                () -> sensorRangeExistValidator.validate(new RangeDTO(from, to)));
        assertEquals("The range {10, 20} not found.", exception.getMessage());
    }
}