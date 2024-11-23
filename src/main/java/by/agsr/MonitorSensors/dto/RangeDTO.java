package by.agsr.MonitorSensors.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RangeDTO {

    @NotNull(message = "The 'from' field must not be empty")
    @Positive(message = "The 'from' field must be a positive integer")
    @JsonAlias("from")
    private Integer rangeFrom;

    @NotNull(message = "The 'to' field cannot be empty")
    @Positive(message = "The 'to' field must be a positive integer")
    @JsonAlias("to")
    private Integer rangeTo;

    @Override
    public String toString() {
        return "RangeDTO{" + rangeFrom + ", " + rangeTo + '}';
    }
}
