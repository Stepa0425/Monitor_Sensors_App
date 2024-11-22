package by.agsr.MonitorSensors.repositories;


import by.agsr.MonitorSensors.models.SensorType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class SensorTypeRepositoryTest {

    @Autowired
    private SensorTypeRepository sensorTypeRepository;

    @Test
    public void injectedRepositoryNotNull(){assertNotNull(sensorTypeRepository);}

    @Test
    public void shouldReturnEmployeeByEmail() {
        Optional<SensorType> sensorType = sensorTypeRepository.findByName("Pressure");
        assertTrue((sensorType.isPresent()));
        assertEquals(sensorType.get().getName(), "Pressure");
    }

    @Test
    public void shouldReturnEmptyEmployee() {
        Optional<SensorType> sensorType = sensorTypeRepository.findByName("NoExistName");
        assertTrue((sensorType.isEmpty()));
    }
}
