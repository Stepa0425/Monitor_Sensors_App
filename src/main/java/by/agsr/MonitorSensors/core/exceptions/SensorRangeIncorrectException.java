package by.agsr.MonitorSensors.core.exceptions;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SensorRangeIncorrectException extends RuntimeException{

    private final Integer from;
    private final Integer to;

    @Override
    public String getMessage() {
        return "The range {" + from + ", " + to + "} not correct.";
    }
}
