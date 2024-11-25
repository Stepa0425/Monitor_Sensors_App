package by.agsr.monitorSensors.core.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Диапазон работы для создания/обновления сенсора")
public class RangeDTO {

    @NotNull(message = "The 'from' field must not be empty")
    @Positive(message = "The 'from' field must be a positive integer")
    @JsonProperty("from")
    @Schema(description = "Начало диапазона", example = "10")
    private Integer rangeFrom;

    @NotNull(message = "The 'to' field must not be empty")
    @Positive(message = "The 'to' field must be a positive integer")
    @JsonProperty("to")
    @Schema(description = "Конец диапазона", example = "20")
    private Integer rangeTo;

    @Override
    public String toString() {
        return "Range{" + rangeFrom + ", " + rangeTo + '}';
    }
}
