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

    @NotNull(message = "Поле 'from' не может быть пустым")
    @Positive(message = "Поле 'from' должно быть положительным целым числом")
    private Integer from;

    @NotNull(message = "Поле 'to' не может быть пустым")
    @Positive(message = "Поле 'to' должно быть положительным целым числом")
    private Integer to;
}
