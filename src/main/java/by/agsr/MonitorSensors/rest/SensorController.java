package by.agsr.MonitorSensors.rest;

import by.agsr.MonitorSensors.core.dto.SensorRequestDTO;
import by.agsr.MonitorSensors.core.services.SensorService;
import jakarta.validation.Valid;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/agsr/api")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class SensorController {

    @Autowired
    private final SensorService sensorService;

    @PreAuthorize("hasRole('Administrator')")
    @PostMapping(path = "/sensors",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<?> createSensor(@RequestBody @Valid SensorRequestDTO sensorRequestDTO) {
        var createdSensor = sensorService.createSensor(sensorRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSensor);
    }

    @PreAuthorize("hasAnyRole('Administrator', 'Viewer')")
    @GetMapping(path = "/sensors", produces = "application/json")
    public ResponseEntity<?> getAllSensors() {
        var sensors = sensorService.getAllSensors();
        return ResponseEntity.ok().body(sensors);
    }

    @PreAuthorize("hasRole('Administrator')")
    @DeleteMapping(path = "/sensors/{id}")
    public ResponseEntity<?> deleteSensor(@PathVariable("id") Long sensorId) {
        sensorService.deleteSensor(sensorId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PreAuthorize("hasRole('Administrator')")
    @PutMapping(path = "/sensors/{id}",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<?> updateSensor(@PathVariable("id") Long sensorId,
                                          @RequestBody @Valid SensorRequestDTO sensorRequestDTO) {
        var updatedSensor = sensorService.updateSensor(sensorId, sensorRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedSensor);
    }

    @PreAuthorize("hasAnyRole('Administrator', 'Viewer')")
    @GetMapping(path = "/sensors/search/byName", produces = "application/json")
    public ResponseEntity<?> findSensorsByName(@RequestParam(value = "name") String sensorName){
          var foundSensors = sensorService.getByName(sensorName);
        return ResponseEntity.status(HttpStatus.OK).body(foundSensors);
    }

    @PreAuthorize("hasAnyRole('Administrator', 'Viewer')")
    @GetMapping(path = "/sensors/search/byModel", produces = "application/json")
    public ResponseEntity<?> findSensorsByModel(@RequestParam(value = "model") String sensorModel){
        var foundSensors = sensorService.getByModel(sensorModel);
        return ResponseEntity.status(HttpStatus.OK).body(foundSensors);
    }
}
