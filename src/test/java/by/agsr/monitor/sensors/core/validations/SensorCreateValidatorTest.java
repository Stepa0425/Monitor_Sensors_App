package by.agsr.monitor.sensors.core.validations;

import by.agsr.monitor.sensors.core.api.dto.RangeDTO;
import by.agsr.monitor.sensors.core.api.dto.SensorRequestDTO;
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

@ExtendWith(MockitoExtension.class)
class SensorCreateValidatorTest {

    private SensorCreateValidator sensorCreateValidator;

    @Mock
    private SensorFieldValidator sensorFieldValidator1;

    @Mock
    private SensorFieldValidator sensorFieldValidator2;

    private final SensorRequestDTO sensorRequestDTO = new SensorRequestDTO();

    @BeforeEach
    public void setUp() {
        List<SensorFieldValidator> validations = List.of(sensorFieldValidator1, sensorFieldValidator2);
        sensorCreateValidator = new SensorCreateValidator(validations);
    }

    @Test
    public void shouldValidateNewSensorWithoutException() {
        var typeName = "Temperature";
        var unitName = "Celsius";
        var range = new RangeDTO(10, 100);

        doNothing().when(sensorFieldValidator1).validateField(sensorRequestDTO);
        doNothing().when(sensorFieldValidator2).validateField(sensorRequestDTO);

        sensorRequestDTO.setType(typeName);
        sensorRequestDTO.setUnit(unitName);
        sensorRequestDTO.setRange(range);

        sensorCreateValidator.validate(sensorRequestDTO);
    }

    @Test
    public void shouldThrowSensorTypeNotFoundException() {
        var typeName = "NonExistType";
        sensorRequestDTO.setType(typeName);
        doThrow(new SensorTypeNotFoundException(typeName))
                .when(sensorFieldValidator1).validateField(sensorRequestDTO);

        var exception = assertThrows(SensorTypeNotFoundException.class,
                () -> sensorCreateValidator.validate(sensorRequestDTO));
        assertEquals("Sensor type with name: NonExistType not found.", exception.getMessage());
    }

    @Test
    public void shouldThrowSensorUnitNotFoundException() {
        var unitName = "NonExistUnit";
        sensorRequestDTO.setUnit(unitName);
        doThrow(new SensorUnitNotFoundException(unitName))
                .when(sensorFieldValidator2).validateField(sensorRequestDTO);

        var exception = assertThrows(SensorUnitNotFoundException.class,
                () -> sensorCreateValidator.validate(sensorRequestDTO));
        assertEquals("Sensor unit with name: NonExistUnit not found.", exception.getMessage());
    }
}