package by.agsr.MonitorSensors.core.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SensorResponseDTO {
    private String name;
    private String model;
    private String description;
    private String location;
    private String unit;
    private String type;

    @JsonAlias("range")
    private RangeDTO range;

}
