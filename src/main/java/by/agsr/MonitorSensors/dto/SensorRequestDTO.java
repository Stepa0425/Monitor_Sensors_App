package by.agsr.MonitorSensors.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SensorRequestDTO {
    private String name;
    private String model;
    private String description;
    private String location;
    private String unit;
    private String type;
    private String range_from;
    private String range_to;
}
