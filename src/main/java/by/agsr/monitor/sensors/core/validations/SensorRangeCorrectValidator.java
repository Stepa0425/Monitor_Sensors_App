package by.agsr.monitor.sensors.core.validations;

import by.agsr.monitor.sensors.core.api.dto.SensorRequestDTO;
import by.agsr.monitor.sensors.core.api.exceptions.SensorRangeIncorrectException;
import org.springframework.stereotype.Component;

@Component
class SensorRangeCorrectValidator implements SensorFieldValidator {

    public void validateField(SensorRequestDTO sensorRequestDTO) {
        var range = sensorRequestDTO.getRange();
        if (range != null
                && range.getRangeFrom() != null
                && range.getRangeTo() != null
                && range.getRangeFrom().compareTo(range.getRangeTo()) >= 0) {
            throw new SensorRangeIncorrectException(range.getRangeFrom(), range.getRangeTo());
        }
    }
}
