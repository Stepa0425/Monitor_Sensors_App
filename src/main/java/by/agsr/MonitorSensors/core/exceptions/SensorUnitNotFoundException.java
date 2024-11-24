package by.agsr.MonitorSensors.core.exceptions;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SensorUnitNotFoundException extends RuntimeException{

    private final String unitName;

    @Override
    public String getMessage() {
        return "Sensor unit with name: " + unitName + " not found.";
    }
}
