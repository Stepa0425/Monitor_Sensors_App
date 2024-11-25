package by.agsr.monitorSensors.core.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Общий объект ошибки")
public class ErrorDTO {

    @Schema(description = "Сообщение об ошибке", example = "Invalid request")
    private String message;

}
