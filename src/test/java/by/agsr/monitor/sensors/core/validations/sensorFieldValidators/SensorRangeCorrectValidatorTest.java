package by.agsr.monitor.sensors.core.validations.sensorFieldValidators;


import by.agsr.monitor.sensors.core.api.dto.RangeDTO;
import by.agsr.monitor.sensors.core.api.dto.SensorRequestDTO;
import by.agsr.monitor.sensors.core.api.exceptions.SensorRangeIncorrectException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SensorRangeCorrectValidatorTest {

    private final SensorRangeCorrectValidator sensorRangeCorrectValidator = new SensorRangeCorrectValidator();

    private final SensorRequestDTO sensorRequestDTO = new SensorRequestDTO();

    @Test
    public void shouldDoNothingWhenRangeCorrect(){
        var range = new RangeDTO(10, 20);
        sensorRequestDTO.setRange(range);
        sensorRangeCorrectValidator.validateField(sensorRequestDTO);
    }

    @Test
    void shouldDoNothingWhenRangeIsNull() {
        sensorRangeCorrectValidator.validateField(sensorRequestDTO);
    }

    @Test
    void shouldDoNothingWhenRangeFromIsNull() {
        var range = new RangeDTO(null, 10);
        sensorRequestDTO.setRange(range);
        sensorRangeCorrectValidator.validateField(sensorRequestDTO);
    }

    @Test
    void shouldDoNothingWhenRangeToIsNull() {
        var range = new RangeDTO(10, null);
        sensorRequestDTO.setRange(range);
        sensorRangeCorrectValidator.validateField(sensorRequestDTO);
    }

    @Test
    void shouldThrowExceptionWhenRangeFromEqualsRangeTo() {
        var range = new RangeDTO(10, 10);
        sensorRequestDTO.setRange(range);
        var exception = assertThrows(SensorRangeIncorrectException.class,
                () -> sensorRangeCorrectValidator.validateField(sensorRequestDTO));
        assertEquals("The range {10, 10} not correct.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenRangeFromGreaterThanRangeTo() {
        var range = new RangeDTO(15, 10);
        sensorRequestDTO.setRange(range);
        var exception = assertThrows(SensorRangeIncorrectException.class,
                () -> sensorRangeCorrectValidator.validateField(sensorRequestDTO));
        assertEquals("The range {15, 10} not correct.", exception.getMessage());    }
}