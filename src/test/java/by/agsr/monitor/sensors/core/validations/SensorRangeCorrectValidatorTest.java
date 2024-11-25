package by.agsr.monitor.sensors.core.validations;


import by.agsr.monitor.sensors.core.api.dto.RangeDTO;
import by.agsr.monitor.sensors.core.api.exceptions.SensorRangeIncorrectException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SensorRangeCorrectValidatorTest {

    private final SensorRangeCorrectValidator sensorRangeCorrectValidator = new SensorRangeCorrectValidator();

    @Test
    public void shouldDoNothingWhenRangeCorrect(){
        var range = new RangeDTO(10, 20);
        sensorRangeCorrectValidator.validateCorrectRange(range);
    }

    @Test
    void shouldDoNothingWhenRangeIsNull() {
        RangeDTO range = null;
        sensorRangeCorrectValidator.validateCorrectRange(range);
    }

    @Test
    void shouldDoNothingWhenRangeFromIsNull() {
        var range = new RangeDTO(null, 10);
        sensorRangeCorrectValidator.validateCorrectRange(range);
    }

    @Test
    void shouldDoNothingWhenRangeToIsNull() {
        var range = new RangeDTO(10, null);
        sensorRangeCorrectValidator.validateCorrectRange(range);
    }

    @Test
    void shouldThrowExceptionWhenRangeFromEqualsRangeTo() {
        var range = new RangeDTO(10, 10);
        var exception = assertThrows(SensorRangeIncorrectException.class,
                () -> sensorRangeCorrectValidator.validateCorrectRange(range));
        assertEquals("The range {10, 10} not correct.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenRangeFromGreaterThanRangeTo() {
        var range = new RangeDTO(15, 10);
        var exception = assertThrows(SensorRangeIncorrectException.class,
                () -> sensorRangeCorrectValidator.validateCorrectRange(range));
        assertEquals("The range {15, 10} not correct.", exception.getMessage());    }
}