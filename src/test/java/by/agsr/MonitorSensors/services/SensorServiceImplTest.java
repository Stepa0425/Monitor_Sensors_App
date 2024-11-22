package by.agsr.MonitorSensors.services;

import by.agsr.MonitorSensors.models.Sensor;
import by.agsr.MonitorSensors.models.SensorType;
import by.agsr.MonitorSensors.repositories.SensorRepository;
import by.agsr.MonitorSensors.validations.SensorValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SensorServiceImplTest {

    @InjectMocks
    private SensorServiceImpl sensorService;

    @Mock
    private SensorRepository sensorRepository;

    @Mock
    private SensorValidator sensorValidator;

    @Test
    void createSensor_shouldCallValidatorAndSaveSensor() {
        Sensor sensor = new Sensor();
        sensor.setType(new SensorType());

        when(sensorRepository.save(sensor)).thenReturn(sensor);
        Sensor createdSensor = sensorService.createSensor(sensor);

        verify(sensorValidator).validateNewSensor(sensor);
        verify(sensorRepository).save(sensor);
        assertEquals(sensor, createdSensor);
    }

    @Test
    void createSensor_shouldThrowException_whenValidationFails() {
        Sensor sensor = new Sensor();
        sensor.setType(new SensorType());

        doThrow(new IllegalArgumentException("Validation failed"))
                .when(sensorValidator).validateNewSensor(sensor);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> sensorService.createSensor(sensor));

        assertEquals("Validation failed", exception.getMessage());
        verify(sensorRepository, never()).save(sensor);
    }
}
