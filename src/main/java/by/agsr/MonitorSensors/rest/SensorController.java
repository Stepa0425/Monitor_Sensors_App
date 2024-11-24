package by.agsr.MonitorSensors.rest;

import by.agsr.MonitorSensors.core.dto.SensorRequestDTO;
import by.agsr.MonitorSensors.core.dto.SensorResponseDTO;
import by.agsr.MonitorSensors.core.services.SensorService;
import jakarta.validation.Valid;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
    public ResponseEntity<?> createSensor(@RequestBody @Valid SensorRequestDTO sensorRequestDTO) {
        try {
            SensorResponseDTO createdSensor = sensorService.createSensor(sensorRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSensor);
        } catch (Exception e) {
            throw new RuntimeException("Error creating new sensor: " + e.getMessage(), e);
        }
    }

    @GetMapping(path = "/", produces = "application/json")
    public ResponseEntity<?> getAllSensors() {
        try {
            List<SensorResponseDTO> sensors = sensorService.getAllSensors();
            return ResponseEntity.ok().body(sensors);
        } catch (Exception e) {
            throw new RuntimeException("Error get all sensors: " + e.getMessage(), e);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteSensor(@PathVariable("id") Long sensorId) {
        try {
            sensorService.deleteSensor(sensorId);
            return ResponseEntity.ok("Sensor with id:" + sensorId + " deleted successfully!");
        } catch (Exception e) {
            throw new RuntimeException("Error deleting sensor: " + e.getMessage(), e);
        }
    }
}
