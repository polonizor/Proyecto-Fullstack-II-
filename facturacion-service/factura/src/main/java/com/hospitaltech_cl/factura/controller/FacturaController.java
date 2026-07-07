package com.hospitaltech_cl.factura.controller;

import com.hospitaltech_cl.factura.model.Factura;
import com.hospitaltech_cl.factura.service.FacturaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/facturas")
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    /**
     * Crear una nueva factura
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> crearFactura(@Valid @RequestBody Factura factura) {
        try {
            log.info("Solicitud para crear nueva factura");
            Factura nuevaFactura = facturaService.crearFactura(factura);
            
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Factura creada exitosamente");
            response.put("factura", nuevaFactura);
            response.put("status", HttpStatus.CREATED.value());
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            log.error("Error al crear factura: {}", e.getMessage(), e);
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            error.put("status", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    /**
     * Obtener factura por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> obtenerFactura(@PathVariable Long id) {
        try {
            Optional<Factura> factura = facturaService.obtenerFacturaById(id);
            
            if (factura.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("factura", factura.get());
                response.put("status", HttpStatus.OK.value());
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> error = new HashMap<>();
                error.put("error", "Factura no encontrada");
                error.put("status", HttpStatus.NOT_FOUND.value());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
        } catch (Exception e) {
            log.error("Error al obtener factura: {}", e.getMessage(), e);
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Obtener factura por número
     */
    @GetMapping("/numero/{numeroFactura}")
    public ResponseEntity<Map<String, Object>> obtenerFacturaPorNumero(@PathVariable String numeroFactura) {
        try {
            Optional<Factura> factura = facturaService.obtenerFacturaByNumero(numeroFactura);
            
            if (factura.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("factura", factura.get());
                response.put("status", HttpStatus.OK.value());
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> error = new HashMap<>();
                error.put("error", "Factura no encontrada");
                error.put("status", HttpStatus.NOT_FOUND.value());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
        } catch (Exception e) {
            log.error("Error al obtener factura: {}", e.getMessage(), e);
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Obtener todas las facturas
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> obtenerTodasLasFacturas() {
        try {
            List<Factura> facturas = facturaService.obtenerTodasLasFacturas();
            
            Map<String, Object> response = new HashMap<>();
            response.put("facturas", facturas);
            response.put("total", facturas.size());
            response.put("status", HttpStatus.OK.value());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error al obtener facturas: {}", e.getMessage(), e);
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Obtener facturas por paciente
     */
    @GetMapping("/paciente/{idPaciente}")
    public ResponseEntity<Map<String, Object>> obtenerFacturasPorPaciente(@PathVariable Long idPaciente) {
        try {
            List<Factura> facturas = facturaService.obtenerFacturasPorPaciente(idPaciente);
            
            Map<String, Object> response = new HashMap<>();
            response.put("facturas", facturas);
            response.put("total", facturas.size());
            response.put("status", HttpStatus.OK.value());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error al obtener facturas del paciente: {}", e.getMessage(), e);
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Obtener facturas por estado
     */
    @GetMapping("/estado/{estado}")
    public ResponseEntity<Map<String, Object>> obtenerFacturasPorEstado(@PathVariable String estado) {
        try {
            Factura.EstadoFactura estadoEnum = Factura.EstadoFactura.valueOf(estado.toUpperCase());
            List<Factura> facturas = facturaService.obtenerFacturasPorEstado(estadoEnum);
            
            Map<String, Object> response = new HashMap<>();
            response.put("facturas", facturas);
            response.put("total", facturas.size());
            response.put("status", HttpStatus.OK.value());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            log.error("Estado inválido: {}", estado);
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Estado de factura inválido");
            error.put("status", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (Exception e) {
            log.error("Error al obtener facturas por estado: {}", e.getMessage(), e);
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Obtener facturas por rango de fecha
     */
    @GetMapping("/fechas")
    public ResponseEntity<Map<String, Object>> obtenerFacturasPorFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        try {
            List<Factura> facturas = facturaService.obtenerFacturasPorRangoFecha(fechaInicio, fechaFin);
            
            Map<String, Object> response = new HashMap<>();
            response.put("facturas", facturas);
            response.put("total", facturas.size());
            response.put("status", HttpStatus.OK.value());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error al obtener facturas por fecha: {}", e.getMessage(), e);
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Actualizar factura
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizarFactura(@PathVariable Long id, @Valid @RequestBody Factura factura) {
        try {
            Factura facturaActualizada = facturaService.actualizarFactura(id, factura);
            
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Factura actualizada exitosamente");
            response.put("factura", facturaActualizada);
            response.put("status", HttpStatus.OK.value());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            log.error("Error al actualizar factura: {}", e.getMessage(), e);
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            error.put("status", HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            log.error("Error inesperado al actualizar factura: {}", e.getMessage(), e);
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Registrar pago de factura
     */
    @PostMapping("/{id}/pagar")
    public ResponseEntity<Map<String, Object>> registrarPago(
            @PathVariable Long id,
            @RequestParam Double monto,
            @RequestParam String metodoPago) {
        try {
            Factura.MetodoPago metodo = Factura.MetodoPago.valueOf(metodoPago.toUpperCase());
            Factura facturaActualizada = facturaService.registrarPago(id, monto, metodo);
            
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Pago registrado exitosamente");
            response.put("factura", facturaActualizada);
            response.put("status", HttpStatus.OK.value());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            log.error("Método de pago inválido: {}", metodoPago);
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Método de pago inválido");
            error.put("status", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (RuntimeException e) {
            log.error("Error al registrar pago: {}", e.getMessage(), e);
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            error.put("status", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (Exception e) {
            log.error("Error inesperado al registrar pago: {}", e.getMessage(), e);
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Anular factura
     */
    @PostMapping("/{id}/anular")
    public ResponseEntity<Map<String, Object>> anularFactura(
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "Solicitud de usuario") String motivo) {
        try {
            Factura facturaAnulada = facturaService.anularFactura(id, motivo);
            
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Factura anulada exitosamente");
            response.put("factura", facturaAnulada);
            response.put("status", HttpStatus.OK.value());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            log.error("Error al anular factura: {}", e.getMessage(), e);
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            error.put("status", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (Exception e) {
            log.error("Error inesperado al anular factura: {}", e.getMessage(), e);
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Eliminar factura
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminarFactura(@PathVariable Long id) {
        try {
            facturaService.eliminarFactura(id);
            
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Factura eliminada exitosamente");
            response.put("status", HttpStatus.OK.value());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            log.error("Error al eliminar factura: {}", e.getMessage(), e);
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            error.put("status", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (Exception e) {
            log.error("Error inesperado al eliminar factura: {}", e.getMessage(), e);
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Obtener estadísticas
     */
    @GetMapping("/estadisticas/resumen")
    public ResponseEntity<Map<String, Object>> obtenerEstadisticas() {
        try {
            Map<String, Object> estadisticas = new HashMap<>();
            estadisticas.put("facturasPendientes", facturaService.contarFacturasPorEstado(Factura.EstadoFactura.PENDIENTE));
            estadisticas.put("facturasPagadas", facturaService.contarFacturasPorEstado(Factura.EstadoFactura.PAGADA));
            estadisticas.put("facturasAnuladas", facturaService.contarFacturasPorEstado(Factura.EstadoFactura.ANULADA));
            estadisticas.put("facturasParcialmentePagadas", facturaService.contarFacturasPorEstado(Factura.EstadoFactura.PARCIALMENTE_PAGADA));
            estadisticas.put("totalPagado", facturaService.obtenerTotalPagado());
            estadisticas.put("status", HttpStatus.OK.value());
            
            return ResponseEntity.ok(estadisticas);
        } catch (Exception e) {
            log.error("Error al obtener estadísticas: {}", e.getMessage(), e);
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
