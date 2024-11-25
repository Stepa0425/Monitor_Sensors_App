package by.agsr.monitor.sensors.core.validations;

import by.agsr.monitor.sensors.core.api.dto.SensorRequestDTO;
import by.agsr.monitor.sensors.core.api.exceptions.SensorRangeNotFoundException;
import by.agsr.monitor.sensors.core.repositories.RangeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class SensorRangeExistValidator implements SensorFieldValidator {

    @Autowired
    private final RangeRepository rangeRepository;

    public void validateField(SensorRequestDTO sensorRequestDTO) {
        var range = sensorRequestDTO.getRange();
        if (range != null && range.getRangeFrom() != null && range.getRangeTo() != null) {
            rangeRepository.findByRangeFromAndRangeTo(range.getRangeFrom(), range.getRangeTo())
                    .orElseThrow(() -> new SensorRangeNotFoundException(range.getRangeFrom(), range.getRangeTo()));
        }
    }
}
