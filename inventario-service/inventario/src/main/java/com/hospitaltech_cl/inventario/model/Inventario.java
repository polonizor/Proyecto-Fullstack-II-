package com.hospitaltech_cl.inventario.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "inventario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ID del producto del microservicio Producto
    private Long productoId;

    private Integer stock;

    private Integer stockMinimo;

    private String ubicacion;
}