package by.agsr.MonitorSensors.repositories;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class RangeRepositoryTest {

    @Autowired
    private RangeRepository rangeRepository;
    @Test
    public void injectedRepositoryNotNull(){assertNotNull(rangeRepository);}
}
