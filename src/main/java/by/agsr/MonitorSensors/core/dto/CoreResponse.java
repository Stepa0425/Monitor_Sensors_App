package by.agsr.MonitorSensors.core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CoreResponse {

    private List<ValidationErrorDTO> errors;

    public boolean hasErrors() {
        return errors != null && !errors.isEmpty();
    }

}
