package com.hospitaltech_cl.habitaciones.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "habitaciones")
public class Habitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El número de habitación es requerido")
    @Column(unique = true, nullable = false)
    private String numeroHabitacion;

    @NotNull(message = "El tipo de habitación es requerido")
    @Enumerated(EnumType.STRING)
    private TipoHabitacion tipo;

    @NotNull(message = "El piso es requerido")
    @Positive(message = "El piso debe ser positivo")
    private Integer piso;

    @NotNull(message = "El estado es requerido")
    @Enumerated(EnumType.STRING)
    private EstadoHabitacion estado = EstadoHabitacion.DISPONIBLE;

    @NotNull(message = "La capacidad es requerida")
    @Positive(message = "La capacidad debe ser mayor a 0")
    private Integer capacidad;

    @NotNull(message = "El precio por noche es requerido")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    private Double precioPorNoche;

    private String descripcion;

    private Boolean tieneBanio = true;
    private Boolean tieneAire = true;
    private Boolean tieneTelevisor = true;
    private Boolean tieneWifi = true;

    private Long idPacienteActual;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum TipoHabitacion {
        SIMPLE,
        DOBLE,
        TRIPLE,
        SUITE,
        UCI
    }

    public enum EstadoHabitacion {
        DISPONIBLE,
        OCUPADA,
        MANTENIMIENTO,
        LIMPIEZA
    }
}
