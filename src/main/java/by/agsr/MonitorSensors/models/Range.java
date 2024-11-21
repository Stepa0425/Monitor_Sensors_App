package by.agsr.MonitorSensors.models;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ranges")
public class Range {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "The 'from' field cannot be empty")
    @Positive(message = "The 'from' field must be a positive integer")
    private Integer from;

    @NotNull(message = "The 'to' field cannot be empty")
    @Positive(message = "The 'to' field must be a positive integer")
    private Integer to;
}
