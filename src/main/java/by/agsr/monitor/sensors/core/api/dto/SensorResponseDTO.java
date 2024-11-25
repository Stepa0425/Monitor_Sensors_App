package by.agsr.monitor.sensors.core.api.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Ответ при получении сенсоров")
public class SensorResponseDTO {

    @Schema(description = "Название сенсора", example = "Barometer")
    private String name;

    @Schema(description = "Модель сенсора", example = "ac-23")
    private String model;

    @Schema(description = "Описание сенсора", example = "a device for measuring atmospheric pressure")
    private String description;

    @Schema(description = "Место расположения сенсора", example = "kitchen")
    private String location;

    @Schema(description = "Единица измерения сенсора", example = "bar")
    private String unit;

    @Schema(description = "Тип сенсора", example = "Pressure")
    private String type;

    @JsonAlias("range")
    @Schema(description = "Диапазон действия сенсора", example = "{10, 20}")
    private RangeDTO range;

}
