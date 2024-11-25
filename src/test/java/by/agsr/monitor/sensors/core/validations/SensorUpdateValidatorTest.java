package by.agsr.monitor.sensors.core.validations;

import by.agsr.monitor.sensors.core.api.dto.RangeDTO;
import by.agsr.monitor.sensors.core.api.dto.SensorRequestDTO;
import by.agsr.monitor.sensors.core.api.exceptions.SensorNotFoundException;
import by.agsr.monitor.sensors.core.api.exceptions.SensorTypeNotFoundException;
import by.agsr.monitor.sensors.core.api.exceptions.SensorUnitNotFoundException;
import by.agsr.monitor.sensors.core.validations.sensorFieldValidators.SensorFieldValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class SensorUpdateValidatorTest {

    private SensorUpdateValidator sensorUpdateValidator;

    @Mock
    private SensorExistValidator sensorExistValidator;

    @Mock
    private SensorFieldValidator sensorFieldValidator1;

    @Mock
    private SensorFieldValidator sensorFieldValidator2;

    private final SensorRequestDTO sensorRequestDTO = new SensorRequestDTO();

    @BeforeEach
    public void setUp() {
        List<SensorFieldValidator> validations = List.of(sensorFieldValidator1, sensorFieldValidator2);
        sensorUpdateValidator = new SensorUpdateValidator(validations, sensorExistValidator);
    }
    @Test
    public void shouldValidateUpdatedSensorWithoutException() {
        var id = 1L;
        var typeName = "Temperature";
        var unitName = "Celsius";
        var range = new RangeDTO(10, 100);

        sensorRequestDTO.setType(typeName);
        sensorRequestDTO.setUnit(unitName);
        sensorRequestDTO.setRange(range);

        doNothing().when(sensorFieldValidator1).validateField(sensorRequestDTO);
        doNothing().when(sensorFieldValidator2).validateField(sensorRequestDTO);
        doNothing().when(sensorExistValidator).validate(id);
        sensorUpdateValidator.validate(id, sensorRequestDTO);

        verify(sensorExistValidator, times(1)).validate(id);
    }

    @Test
    public void shouldThrowSensorTypeNotFoundException() {
        var id = 1L;
        var typeName = "NonExistType";
        sensorRequestDTO.setType(typeName);
        doThrow(new SensorTypeNotFoundException(typeName))
                .when(sensorFieldValidator1).validateField(sensorRequestDTO);

        var exception = assertThrows(SensorTypeNotFoundException.class,
                () -> sensorUpdateValidator.validate(id, sensorRequestDTO));
        assertEquals("Sensor type with name: NonExistType not found.", exception.getMessage());
    }

    @Test
    public void shouldThrowSensorUnitNotFoundException() {
        var id = 1L;
        var unitName = "NonExistUnit";
        sensorRequestDTO.setUnit(unitName);
        doThrow(new SensorUnitNotFoundException(unitName))
                .when(sensorFieldValidator2).validateField(sensorRequestDTO);

        var exception = assertThrows(SensorUnitNotFoundException.class,
                () -> sensorUpdateValidator.validate(id, sensorRequestDTO));
        assertEquals("Sensor unit with name: NonExistUnit not found.", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenSensorDoesNotExist() {
        var invalidSensorId = 999L;
        doThrow(new SensorNotFoundException(invalidSensorId))
                .when(sensorExistValidator).validate(invalidSensorId);
        var exception = assertThrows(SensorNotFoundException.class,
                () -> sensorUpdateValidator.validate(invalidSensorId, sensorRequestDTO));
        assertEquals("Sensor with id: 999 not found.", exception.getMessage());
    }

}