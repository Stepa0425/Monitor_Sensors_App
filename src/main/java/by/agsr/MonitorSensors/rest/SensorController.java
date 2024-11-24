package by.agsr.MonitorSensors.rest;

import by.agsr.MonitorSensors.core.dto.SensorRequestDTO;
import by.agsr.MonitorSensors.core.dto.SensorResponseDTO;
import by.agsr.MonitorSensors.core.services.SensorService;
import jakarta.validation.Valid;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RestController
@RequestMapping("/agsr/monitor/sensors")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class SensorController {

    @Autowired
    private final SensorService sensorService;

    @PostMapping(path = "/",
            consumes = "application/json",
            produces = "application/json")
    public SensorResponseDTO createSensor(@RequestBody @Valid SensorRequestDTO sensorRequestDTO) {
        try {
            return sensorService.createSensor(sensorRequestDTO);
        } catch (Exception e) {
            throw new RuntimeException("Error creating new sensor: " + e.getMessage(), e);
        }
    }

    @GetMapping(path = "/", produces = "application/json")
    public List<SensorResponseDTO> getAllSensors() {
        try {
            return sensorService.getAllSensors();
        } catch (Exception e) {
            throw new RuntimeException("Error get all sensors: " + e.getMessage(), e);
        }
    }


}
