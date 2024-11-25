package by.agsr.monitorSensors.core.models;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.JoinColumn;

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

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "model", nullable = false)
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

    @Column(name = "location")
    private String location;

    @Column(name = "description")
    private String description;
}


