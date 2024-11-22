package by.agsr.MonitorSensors.models;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.JoinColumn;

import jakarta.validation.constraints.NotEmpty;

import jakarta.validation.constraints.Size;
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

    @Size(max = 30, min = 3, message = "The 'name' field should contain at least 3 and no more than 30 characters")
    @NotEmpty
    @Column(name = "name")
    private String name;

    @Size(max = 15, message = "The 'model' field should contain no more than 15 characters")
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

    @Size(min = 40, message = "The 'location' field should have no more than 40 characters")
    @Column(name = "location")
    private String location;

    @Size(max = 200, message = "The 'location' field should have no more than 200 characters")
    @Column(name = "description")
    private String description;
}


