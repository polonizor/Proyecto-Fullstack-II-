package com.hospitaltech_cl.factura.repository;

import com.hospitaltech_cl.factura.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {

    Optional<Factura> findByNumeroFactura(String numeroFactura);

    List<Factura> findByIdPaciente(Long idPaciente);

    List<Factura> findByEstado(Factura.EstadoFactura estado);

    @Query("SELECT f FROM Factura f WHERE f.fechaFactura BETWEEN :fechaInicio AND :fechaFin")
    List<Factura> findByFechaFacturaBetween(@Param("fechaInicio") LocalDate fechaInicio,
                                           @Param("fechaFin") LocalDate fechaFin);

    @Query("SELECT f FROM Factura f WHERE f.idPaciente = :idPaciente AND f.estado = :estado")
    List<Factura> findByIdPacienteAndEstado(@Param("idPaciente") Long idPaciente,
                                           @Param("estado") Factura.EstadoFactura estado);

    @Query("SELECT COUNT(f) FROM Factura f WHERE f.estado = :estado")
    long countByEstado(@Param("estado") Factura.EstadoFactura estado);

    @Query("SELECT COALESCE(SUM(f.montoTotal), 0) FROM Factura f WHERE f.estado = 'PAGADA'")
    Double sumTotalPagado();
}
