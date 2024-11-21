package by.agsr.MonitorSensors.models;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.JoinColumn;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sensors")
public class Sensor {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(value = 3, message = "Поле 'name' должно иметь не менее 3 символов")
    @Max(value = 30, message = "Поле 'name' должно иметь не более 30 символов")
    @NotEmpty
    @Column(name = "name")
    private String name;

    @Max(value = 15, message = "Поле 'model' должно иметь не более 15 символов")
    @NotEmpty
    @Column(name = "model")
    private String model;


    @ManyToOne
    @JoinColumn(name = "range_id", nullable = false)
    private Range range;

    @ManyToOne
    @JoinColumn(name = "sensor_type_id", nullable = false)
    private SensorType type;

    @ManyToOne
    @JoinColumn(name = "sensor_unit_id")
    private SensorUnit unit;

    @Max(value = 40, message = "The 'location' field should have no more than 40 characters")
    @Column(name = "location")
    private String location;

    @Max(value = 200, message = "The 'location' field should have no more than 200 characters")
    @Column(name = "description")
    private String description;
}


