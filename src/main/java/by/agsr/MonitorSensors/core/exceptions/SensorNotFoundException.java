package by.agsr.MonitorSensors.core.exceptions;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SensorNotFoundException extends RuntimeException{

    private final Long id;

    @Override
    public String getMessage() {
        return "Sensor with id: " + id + " not found.";
    }
}
