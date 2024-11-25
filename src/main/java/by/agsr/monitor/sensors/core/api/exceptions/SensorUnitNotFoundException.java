package by.agsr.monitor.sensors.core.api.exceptions;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SensorUnitNotFoundException extends RuntimeException{

    private final String unitName;

    @Override
    public String getMessage() {
        return "Sensor unit with name: " + unitName + " not found.";
    }
}
