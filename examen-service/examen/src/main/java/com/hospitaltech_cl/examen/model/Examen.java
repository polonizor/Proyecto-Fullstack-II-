package com.hospitaltech_cl.examen.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "examenes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Examen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long pacienteId;

    private Long medicoId;

    private String tipo;

    private String resultado;

    private String fecha;

    private String estado;
}