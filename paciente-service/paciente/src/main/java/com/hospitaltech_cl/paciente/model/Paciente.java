package com.hospitaltech_cl.paciente.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pacientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rut;
    private String nombre;
    private String apellido;
    private Integer edad;
    private String sexo;
    private String direccion;
    private String telefono;
    private String email;
}