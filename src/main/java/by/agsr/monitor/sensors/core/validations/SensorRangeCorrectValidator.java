package by.agsr.monitor.sensors.core.validations;

import by.agsr.monitor.sensors.core.api.dto.RangeDTO;
import by.agsr.monitor.sensors.core.api.exceptions.SensorRangeIncorrectException;
import org.springframework.stereotype.Component;

@Component
class SensorRangeCorrectValidator {

    public void validateCorrectRange(RangeDTO range) {
        if (range != null
                && range.getRangeFrom() != null
                && range.getRangeTo() != null
                && range.getRangeFrom().compareTo(range.getRangeTo()) >= 0) {
            throw new SensorRangeIncorrectException(range.getRangeFrom(), range.getRangeTo());
        }
    }
}
