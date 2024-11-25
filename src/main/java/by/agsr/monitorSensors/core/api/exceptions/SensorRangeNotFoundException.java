package by.agsr.monitorSensors.core.api.exceptions;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SensorRangeNotFoundException extends RuntimeException {

    private final Integer from;
    private final Integer to;

    @Override
    public String getMessage() {
        return "The range {" + from + ", " + to + "} not found.";
    }
}
