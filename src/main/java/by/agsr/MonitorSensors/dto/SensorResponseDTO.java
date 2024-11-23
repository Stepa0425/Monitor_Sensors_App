package by.agsr.MonitorSensors.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SensorResponseDTO extends CoreResponse {
    private String name;
    private String model;
    private String description;
    private String location;
    private String unit;
    private String type;

    @JsonAlias("range")
    private RangeDTO rangeDTO;

    private List<ValidationErrorDTO> errors;
}
