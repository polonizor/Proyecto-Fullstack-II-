package com.hospitaltech_cl.factura.service;

import com.hospitaltech_cl.factura.model.Factura;
import com.hospitaltech_cl.factura.model.DetalleFactura;
import com.hospitaltech_cl.factura.repository.FacturaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    @Transactional
    public Factura crearFactura(Factura factura) {
        log.info("Creando nueva factura para paciente: {}", factura.getIdPaciente());
        
        // Calcular monto total si hay detalles
        if (factura.getDetalles() != null && !factura.getDetalles().isEmpty()) {
            Double total = factura.getDetalles().stream()
                    .mapToDouble(DetalleFactura::getSubtotal)
                    .sum();
            factura.setMontoTotal(total);
        }
        
        // Calcular monto a pagar
        Double montoAPagar = factura.getMontoTotal() - (factura.getDescuento() != null ? factura.getDescuento() : 0);
        factura.setMontoPagar(montoAPagar);
        
        Factura savedFactura = facturaRepository.save(factura);
        log.info("Factura creada exitosamente con número: {}", savedFactura.getNumeroFactura());
        return savedFactura;
    }

    @Transactional(readOnly = true)
    public Optional<Factura> obtenerFacturaById(Long id) {
        log.info("Obteniendo factura con ID: {}", id);
        return facturaRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Factura> obtenerFacturaByNumero(String numeroFactura) {
        log.info("Obteniendo factura con número: {}", numeroFactura);
        return facturaRepository.findByNumeroFactura(numeroFactura);
    }

    @Transactional(readOnly = true)
    public List<Factura> obtenerFacturasPorPaciente(Long idPaciente) {
        log.info("Obteniendo facturas del paciente: {}", idPaciente);
        return facturaRepository.findByIdPaciente(idPaciente);
    }

    @Transactional(readOnly = true)
    public List<Factura> obtenerFacturasPorEstado(Factura.EstadoFactura estado) {
        log.info("Obteniendo facturas con estado: {}", estado);
        return facturaRepository.findByEstado(estado);
    }

    @Transactional(readOnly = true)
    public List<Factura> obtenerFacturasPorRangoFecha(LocalDate fechaInicio, LocalDate fechaFin) {
        log.info("Obteniendo facturas entre {} y {}", fechaInicio, fechaFin);
        return facturaRepository.findByFechaFacturaBetween(fechaInicio, fechaFin);
    }

    @Transactional
    public Factura actualizarFactura(Long id, Factura facturaActualizada) {
        log.info("Actualizando factura con ID: {}", id);
        
        return facturaRepository.findById(id).map(factura -> {
            factura.setNumeroFactura(facturaActualizada.getNumeroFactura());
            factura.setNombrePaciente(facturaActualizada.getNombrePaciente());
            factura.setRutPaciente(facturaActualizada.getRutPaciente());
            factura.setFechaFactura(facturaActualizada.getFechaFactura());
            factura.setMontoTotal(facturaActualizada.getMontoTotal());
            factura.setDescuento(facturaActualizada.getDescuento());
            factura.setDescripcion(facturaActualizada.getDescripcion());
            
            // Recalcular monto a pagar
            Double montoAPagar = facturaActualizada.getMontoTotal() - 
                    (facturaActualizada.getDescuento() != null ? facturaActualizada.getDescuento() : 0);
            factura.setMontoPagar(montoAPagar);
            
            return facturaRepository.save(factura);
        }).orElseThrow(() -> new RuntimeException("Factura no encontrada con ID: " + id));
    }

    @Transactional
    public Factura registrarPago(Long id, Double monto, Factura.MetodoPago metodoPago) {
        log.info("Registrando pago de {} para factura ID: {}", monto, id);
        
        return facturaRepository.findById(id).map(factura -> {
            if (factura.getEstado() == Factura.EstadoFactura.ANULADA) {
                throw new RuntimeException("No se puede pagar una factura anulada");
            }
            
            if (monto > factura.getMontoPagar()) {
                throw new RuntimeException("El monto excede el monto a pagar");
            }
            
            factura.setMetodoPago(metodoPago);
            factura.setFechaPago(LocalDate.now());
            
            if (monto.equals(factura.getMontoPagar())) {
                factura.setEstado(Factura.EstadoFactura.PAGADA);
            } else if (monto > 0) {
                factura.setEstado(Factura.EstadoFactura.PARCIALMENTE_PAGADA);
                factura.setMontoPagar(factura.getMontoPagar() - monto);
            }
            
            return facturaRepository.save(factura);
        }).orElseThrow(() -> new RuntimeException("Factura no encontrada con ID: " + id));
    }

    @Transactional
    public Factura anularFactura(Long id, String motivo) {
        log.info("Anulando factura con ID: {}", id);
        
        return facturaRepository.findById(id).map(factura -> {
            if (factura.getEstado() == Factura.EstadoFactura.PAGADA) {
                throw new RuntimeException("No se puede anular una factura ya pagada");
            }
            
            factura.setEstado(Factura.EstadoFactura.ANULADA);
            factura.setDescripcion("ANULADA - " + motivo);
            return facturaRepository.save(factura);
        }).orElseThrow(() -> new RuntimeException("Factura no encontrada con ID: " + id));
    }

    @Transactional
    public void eliminarFactura(Long id) {
        log.info("Eliminando factura con ID: {}", id);
        
        Factura factura = facturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada con ID: " + id));
        
        if (factura.getEstado() == Factura.EstadoFactura.PAGADA) {
            throw new RuntimeException("No se puede eliminar una factura pagada");
        }
        
        facturaRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Factura> obtenerTodasLasFacturas() {
        log.info("Obteniendo todas las facturas");
        return facturaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public long contarFacturasPorEstado(Factura.EstadoFactura estado) {
        log.info("Contando facturas con estado: {}", estado);
        return facturaRepository.countByEstado(estado);
    }

    @Transactional(readOnly = true)
    public Double obtenerTotalPagado() {
        log.info("Obteniendo total pagado");
        return facturaRepository.sumTotalPagado();
    }
}
