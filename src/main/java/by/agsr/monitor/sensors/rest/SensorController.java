package by.agsr.monitor.sensors.rest;

import by.agsr.monitor.sensors.core.api.dto.SensorRequestDTO;
import by.agsr.monitor.sensors.core.api.dto.SensorResponseDTO;
import by.agsr.monitor.sensors.core.api.dto.ValidationErrorDTO;
import by.agsr.monitor.sensors.core.services.SensorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/agsr/api")
@Tag(name = "Sensor Controller", description = "Управление сенсорами")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class SensorController {


    @Autowired
    private final SensorService sensorService;


    @Operation(summary = "Создать сенсор", description = """
                    Добавляет новый сенсор в систему.
                    Требуется роль 'Administrator'.
                    Возвращает информацию о созданном сенсоре.
                    """)
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Сенсор успешно создан.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SensorResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = """
                            Некорректные данные запроса. Возможно,
                            отсутствуют обязательные поля или поля не соответствуют требуемым форматам.
                            """,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrorDTO.class)))})
    @PreAuthorize("hasRole('Administrator')")
    @PostMapping(path = "/sensors",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<?> createSensor(@RequestBody @Valid SensorRequestDTO sensorRequestDTO) {
        var createdSensor = sensorService.createSensor(sensorRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSensor);
    }


    @Operation(summary = "Получить все сенсоры", description = "Возвращает список всех сенсоров.")
    @ApiResponse(
            responseCode = "200", description = "Успешное выполнение запроса",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = SensorResponseDTO.class)))
    @PreAuthorize("hasAnyRole('Administrator', 'Viewer')")
    @GetMapping(path = "/sensors", produces = "application/json")
    public ResponseEntity<?> getAllSensors() {
        var sensors = sensorService.getAllSensors();
        return ResponseEntity.ok().body(sensors);
    }


    @Operation(summary = "Удалить сенсор", description = """
            Удаляет сенсор по указанному ID. 
            Требуется роль 'Administrator'. Ничего не возвращает.
            """)
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204", description = "Сенсор успешно удалён."),
            @ApiResponse(
                    responseCode = "400", description = "Сенсор с указанным ID не найден.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrorDTO.class)))})
    @PreAuthorize("hasRole('Administrator')")
    @DeleteMapping(path = "/sensors/{id}")
    public ResponseEntity<?> deleteSensor(@PathVariable("id") Long sensorId) {
        sensorService.deleteSensor(sensorId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @Operation(summary = "Обновить сенсор",
            description = "Обновляет данные существующего сенсора по ID.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = """
                    Сенсор успешно обновлен. Требуется роль 'Administrator'.
                    Возвращает обновленный сенсор.
                    """,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SensorResponseDTO.class))),
            @ApiResponse(
                    responseCode = "400", description = "Некорректные данные запроса",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrorDTO.class)))})
    @PreAuthorize("hasRole('Administrator')")
    @PutMapping(path = "/sensors/{id}",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<?> updateSensor(@PathVariable("id") Long sensorId,
                                          @RequestBody @Valid SensorRequestDTO sensorRequestDTO) {
        var updatedSensor = sensorService.updateSensor(sensorId, sensorRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedSensor);
    }


    @Operation(summary = "Найти сенсор по названию", description = """
            Возвращает список сенсоров по частичному совпадению названия.
            Если сенсоры найдены, возвращает их список.
            """)
    @ApiResponse(
            responseCode = "200",
            description = "Успешное выполнение запроса, возвращен список сенсоров, соответствующих запросу.",
            content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = SensorResponseDTO.class)))
    @PreAuthorize("hasAnyRole('Administrator', 'Viewer')")
    @GetMapping(path = "/sensors/search/byName", produces = "application/json")
    public ResponseEntity<?> findSensorsByName(@RequestParam(value = "name") String sensorName) {
        var foundSensors = sensorService.getSensorsByName(sensorName);
        return ResponseEntity.status(HttpStatus.OK).body(foundSensors);
    }


    @Operation(summary = "Найти сенсор по модели", description = """
            Возвращает список сенсоров по частичному совпадению модели.
            Если сенсоры найдены, возвращает их список.""")
    @ApiResponse(
            responseCode = "200", description = "Успешное выполнение запроса, возвращен список сенсоров, соответствующих запросу.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = SensorResponseDTO.class)))
    @PreAuthorize("hasAnyRole('Administrator', 'Viewer')")
    @GetMapping(path = "/sensors/search/byModel", produces = "application/json")
    public ResponseEntity<?> findSensorsByModel(@RequestParam(value = "model") String sensorModel) {
        var foundSensors = sensorService.getSensorsByModel(sensorModel);
        return ResponseEntity.status(HttpStatus.OK).body(foundSensors);
    }
}
