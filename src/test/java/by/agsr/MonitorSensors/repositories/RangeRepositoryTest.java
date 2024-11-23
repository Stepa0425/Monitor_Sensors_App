package by.agsr.MonitorSensors.repositories;

import by.agsr.MonitorSensors.models.Range;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class RangeRepositoryTest {

    @Autowired
    private RangeRepository rangeRepository;

    @Test
    public void injectedRepositoryNotNull(){assertNotNull(rangeRepository);}

    @Test
    void shouldReturnRangeWhenExists() {
        Integer rangeFrom = 15;
        Integer rangeTo = 20;
        Range expectedRange = new Range();
        expectedRange.setId(1L);
        expectedRange.setRangeFrom(rangeFrom);
        expectedRange.setRangeTo(rangeTo);

        Optional<Range> actualRange = rangeRepository.findByRangeFromAndRangeTo(rangeFrom, rangeTo);

        assertTrue(actualRange.isPresent());
        assertEquals(expectedRange.getRangeFrom(), actualRange.get().getRangeFrom());
        assertEquals(expectedRange.getRangeTo(), actualRange.get().getRangeTo());
    }

    @Test
    void shouldReturnEmptyWhenNotExists() {
        Integer rangeFrom = 1000;
        Integer rangeTo = 2023;
        Optional<Range> actualRange = rangeRepository.findByRangeFromAndRangeTo(rangeFrom, rangeTo);

        assertTrue(actualRange.isEmpty());
    }
}
