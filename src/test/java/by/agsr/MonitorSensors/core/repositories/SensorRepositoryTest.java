package by.agsr.MonitorSensors.core.repositories;

import by.agsr.MonitorSensors.core.models.Sensor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class SensorRepositoryTest {

    @Autowired
    private SensorRepository sensorRepository;

    @Test
    public void injectedRepositoryNotNull(){assertNotNull(sensorRepository);}

    @Test
    public void shouldReturnFoundSensors() {
        List<Sensor> foundSensors = sensorRepository.findByNameContaining("meter");

        assertEquals(1, foundSensors.size());
    }

    @Test
    public void shouldReturnEmptyList() {
        List<Sensor> foundSensors = sensorRepository.findByNameContaining("Nonexistent");

        assertTrue(foundSensors.isEmpty());
    }

    @Test
    public void shouldReturnAllSensors() {
        List<Sensor> foundSensors = sensorRepository.findByNameContaining("");

        assertEquals(1, foundSensors.size());
    }
}
