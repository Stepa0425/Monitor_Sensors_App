package by.agsr.monitor.sensors.core.api.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
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
@Schema(description = "Запрос на создание сенсора")
public class SensorRequestDTO {

    @Schema(description = "Название сенсора", example = "Barometer")
    @NotBlank(message = "The field must not be empty")
    @Size(max = 30, min = 3, message = "The 'name' field should contain at least 3 and no more than 30 characters")
    private String name;

    @Schema(description = "Модель сенсора", example = "ac-23")
    @NotBlank(message = "The field must not be empty")
    @Size(max = 15, message = "The field should contain no more than 15 characters")
    private String model;

    @Schema(description = "Описание сенсора", example = "a device for measuring atmospheric pressure")
    @Size(max = 200, message = "The field should have no more than 200 characters")
    private String description;

    @Schema(description = "Место расположения сенсора", example = "kitchen")
    @Size(max = 40, message = "The field should have no more than 40 characters")
    private String location;

    @Schema(description = "Единица измерения сенсора", example = "bar")
    private String unit;

    @Schema(description = "Тип сенсора", example = "Pressure")
    @NotBlank(message = "The field must not be empty")
    private String type;

    @Valid
    @JsonAlias("range")
    @NotNull(message = "The field must not be empty")
    @Schema(description = "Диапазон действия сенсора", example = "{10, 20}")
    private RangeDTO range;
}
