package by.agsr.MonitorSensors.core.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationErrorDTO extends ErrorDTO {

    private String field;

    public ValidationErrorDTO(String field, String message) {
        this.field = field;
        super.setMessage(message);
    }
}
