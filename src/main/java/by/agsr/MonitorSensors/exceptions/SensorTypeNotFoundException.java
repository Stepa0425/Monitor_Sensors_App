package by.agsr.MonitorSensors.exceptions;

public class SensorTypeNotFoundException extends RuntimeException {
    public SensorTypeNotFoundException(String name) {
        super("Sensor type with name: " + name + " not found.");
    }
}
