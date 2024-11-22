package by.agsr.MonitorSensors.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationErrorDTO {
    private String field;
    private String message;
}
