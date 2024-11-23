package by.agsr.MonitorSensors.repositories;

import by.agsr.MonitorSensors.models.SensorUnit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class SensorUnitRepositoryTest {

    @Autowired
    private SensorUnitRepository sensorUnitRepository;

    @Test
    public void injectedRepositoryNotNull(){assertNotNull(sensorUnitRepository);}

    @Test
    public void shouldReturnEmployeeByEmail() {
        Optional<SensorUnit> sensorUnit = sensorUnitRepository.findByName("bar");
        assertTrue((sensorUnit.isPresent()));
        assertEquals(sensorUnit.get().getName(), "bar");
    }

    @Test
    public void shouldReturnEmptyEmployee() {
        Optional<SensorUnit> sensorUnit = sensorUnitRepository.findByName("NoExistBar");
        assertTrue((sensorUnit.isEmpty()));
    }
}
