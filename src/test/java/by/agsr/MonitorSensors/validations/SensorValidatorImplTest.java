package by.agsr.MonitorSensors.validations;

import by.agsr.MonitorSensors.exceptions.SensorTypeNotFoundException;
import by.agsr.MonitorSensors.models.Sensor;
import by.agsr.MonitorSensors.models.SensorType;
import by.agsr.MonitorSensors.repositories.SensorTypeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SensorValidatorImplTest {

    @InjectMocks
    private SensorValidatorImpl sensorValidator;

    @Mock
    private SensorTypeRepository sensorTypeRepository;

    @Test
    void validateNewSensor_shouldThrowException_whenSensorTypeIsNull() {
        Sensor sensor = new Sensor();
        sensor.setType(null);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> sensorValidator.validateNewSensor(sensor));

        assertEquals("Sensor type cannot be null", exception.getMessage());
    }

    @Test
    void validateNewSensor_shouldThrowException_whenSensorTypeNotFound() {
        SensorType sensorType = new SensorType();
        sensorType.setName("Pressure");
        Sensor sensor = new Sensor();
        sensor.setType(sensorType);

        when(sensorTypeRepository.findByName(sensorType.getName())).thenReturn(Optional.empty());
        SensorTypeNotFoundException exception = assertThrows(SensorTypeNotFoundException.class,
                () -> sensorValidator.validateNewSensor(sensor));

        assertEquals("Sensor type with name: Pressure not found.", exception.getMessage());
    }

    @Test
    void validateNewSensor_shouldNotThrowException_whenSensorTypeExists() {
        SensorType sensorType = new SensorType();
        sensorType.setName("Pressure");
        Sensor sensor = new Sensor();
        sensor.setType(sensorType);
        when(sensorTypeRepository.findByName(sensorType.getName())).thenReturn(Optional.of(sensorType));

        assertDoesNotThrow(() -> sensorValidator.validateNewSensor(sensor));
    }
}
