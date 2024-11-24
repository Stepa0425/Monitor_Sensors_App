package by.agsr.MonitorSensors.core.exceptions;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SensorRangeNotFoundException extends RuntimeException {

    private final Integer from;
    private final Integer to;

    @Override
    public String getMessage() {
        return "Range {" + from + ", " + to + "} not found.";
    }
}
