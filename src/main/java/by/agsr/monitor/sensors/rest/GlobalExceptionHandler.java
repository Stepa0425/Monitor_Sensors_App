package by.agsr.monitor.sensors.rest;

import by.agsr.monitor.sensors.core.api.dto.ErrorDTO;
import by.agsr.monitor.sensors.core.api.dto.ValidationErrorDTO;
import by.agsr.monitor.sensors.core.api.exceptions.SensorNotFoundException;
import by.agsr.monitor.sensors.core.api.exceptions.SensorRangeIncorrectException;
import by.agsr.monitor.sensors.core.api.exceptions.SensorRangeNotFoundException;
import by.agsr.monitor.sensors.core.api.exceptions.SensorTypeNotFoundException;
import by.agsr.monitor.sensors.core.api.exceptions.SensorUnitNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ErrorController.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SensorNotFoundException.class)
    @ResponseBody
    public ResponseEntity<?> processSensorNotFoundException(Exception e) {
        logger.error("Sensor existing error", e);
        var error = new ValidationErrorDTO("id", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(SensorRangeNotFoundException.class)
    @ResponseBody
    public ResponseEntity<?> processSensorRangeNotFoundException(Exception e) {
        logger.error("Sensor's range existing error", e);
        var error = new ValidationErrorDTO("range", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(SensorTypeNotFoundException.class)
    @ResponseBody
    public ResponseEntity<?> processSensorTypeNotFoundException(Exception e) {
        logger.error("Sensor's type existing error", e);
        var error = new ValidationErrorDTO("type", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(SensorUnitNotFoundException.class)
    @ResponseBody
    public ResponseEntity<?> processSensorUnitNotFoundException(Exception e) {
        logger.error("Sensor's unit existing error", e);
        var error = new ValidationErrorDTO("unit", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(SensorRangeIncorrectException.class)
    @ResponseBody
    public ResponseEntity<?> processSensorRangeIncorrectException(Exception e) {
        logger.error("Sensor's range incorrect error", e);
        var error = new ValidationErrorDTO("range", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ErrorDTO processException(Exception e) {
        logger.error("Unexpected error", e);
        return new ErrorDTO(e.getMessage());
    }
}