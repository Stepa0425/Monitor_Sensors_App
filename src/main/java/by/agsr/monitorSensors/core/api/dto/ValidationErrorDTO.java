package by.agsr.monitorSensors.core.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Объект ошибки валидации")
public class ValidationErrorDTO extends ErrorDTO {

    @Schema(description = "Поле, вызвавшее ошибку", example = "name")
    private String field;

    public ValidationErrorDTO(String field, String message) {
        this.field = field;
        super.setMessage(message);
    }
}
