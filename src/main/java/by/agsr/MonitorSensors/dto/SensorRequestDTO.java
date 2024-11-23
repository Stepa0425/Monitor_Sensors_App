package by.agsr.MonitorSensors.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SensorRequestDTO {

    @NotBlank(message = "The field must not be empty")
    @Size(max = 30, min = 3, message = "The 'name' field should contain at least 3 and no more than 30 characters")
    private String name;

    @NotBlank(message = "The field must not be empty")
    @Size(max = 15, message = "The field should contain no more than 15 characters")
    private String model;

    @Size(max = 200, message = "The field should have no more than 200 characters")
    private String description;

    @Size(max = 40, message = "The field should have no more than 40 characters")
    private String location;

    private String unit;

    @NotBlank(message = "The field must be not empty")
    private String type;

    @JsonAlias("range")
    @NotNull(message = "The field must not be empty")
    private RangeDTO range;
}
