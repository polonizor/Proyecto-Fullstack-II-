package com.hospitaltech_cl.factura.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "facturas")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El número de factura es requerido")
    @Column(unique = true, nullable = false)
    private String numeroFactura;

    @NotNull(message = "El ID del paciente es requerido")
    private Long idPaciente;

    @NotBlank(message = "El nombre del paciente es requerido")
    private String nombrePaciente;

    @NotBlank(message = "El RUT del paciente es requerido")
    private String rutPaciente;

    @NotNull(message = "La fecha de facturación es requerida")
    private LocalDate fechaFactura;

    @NotNull(message = "El monto total es requerido")
    @DecimalMin(value = "0.0", inclusive = false, message = "El monto debe ser mayor a 0")
    private Double montoTotal;

    @DecimalMin(value = "0.0", message = "El descuento no puede ser negativo")
    private Double descuento = 0.0;

    @NotNull(message = "El monto a pagar es requerido")
    private Double montoPagar;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoFactura estado = EstadoFactura.PENDIENTE;

    @Enumerated(EnumType.STRING)
    private MetodoPago metodoPago;

    private String descripcion;

    private LocalDate fechaPago;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "factura_id")
    private List<DetalleFactura> detalles;

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

    public enum EstadoFactura {
        PENDIENTE,
        PAGADA,
        ANULADA,
        PARCIALMENTE_PAGADA
    }

    public enum MetodoPago {
        EFECTIVO,
        TARJETA_CREDITO,
        TARJETA_DEBITO,
        TRANSFERENCIA_BANCARIA,
        CHEQUE
    }
}
