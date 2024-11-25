package by.agsr.monitorSensors.core.api.exceptions;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SensorTypeNotFoundException extends RuntimeException{

    private final String typeName;

    @Override
    public String getMessage() {
        return "Sensor type with name: " + typeName + " not found.";
    }
}
