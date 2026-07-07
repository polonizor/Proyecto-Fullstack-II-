package com.hospitaltech_cl.factura.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "detalle_facturas")
public class DetalleFactura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La descripción del servicio es requerida")
    private String descripcion;

    @NotNull(message = "La cantidad es requerida")
    @Positive(message = "La cantidad debe ser mayor a 0")
    private Integer cantidad;

    @NotNull(message = "El valor unitario es requerido")
    @DecimalMin(value = "0.0", inclusive = false, message = "El valor unitario debe ser mayor a 0")
    private Double valorUnitario;

    @NotNull(message = "El subtotal es requerido")
    @DecimalMin(value = "0.0", inclusive = false, message = "El subtotal debe ser mayor a 0")
    private Double subtotal;

    private String codigoServicio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "factura_id", nullable = false)
    private Factura factura;

    @PrePersist
    public void calcularSubtotal() {
        if (cantidad != null && valorUnitario != null) {
            this.subtotal = cantidad * valorUnitario;
        }
    }
}
