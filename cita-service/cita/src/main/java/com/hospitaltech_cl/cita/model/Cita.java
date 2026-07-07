package com.hospitaltech_cl.cita.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name="citas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long pacienteId;

    private Long medicoId;

    private Long especialidadId;

    private LocalDate fecha;

    private LocalTime hora;

    private String estado;
}