package by.agsr.MonitorSensors.rest;

import by.agsr.MonitorSensors.core.dto.SensorRequestDTO;
import by.agsr.MonitorSensors.core.services.SensorService;
import jakarta.validation.Valid;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


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
        var createdSensor = sensorService.createSensor(sensorRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSensor);
    }

    @GetMapping(path = "/", produces = "application/json")
    public ResponseEntity<?> getAllSensors() {
        var sensors = sensorService.getAllSensors();
        return ResponseEntity.ok().body(sensors);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteSensor(@PathVariable("id") Long sensorId) {
        sensorService.deleteSensor(sensorId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping(path = "/{id}",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<?> updateSensor(@PathVariable("id") Long sensorId,
                                          @RequestBody @Valid SensorRequestDTO sensorRequestDTO){
        var updatedSensor = sensorService.updateSensor(sensorId, sensorRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedSensor);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<?> findSensorsByName(@RequestParam(value = "name" ,required = false) String sensorName){
        var foundSensors = sensorService.getByName(sensorName);
        return ResponseEntity.status(HttpStatus.OK).body(foundSensors);
    }
}
