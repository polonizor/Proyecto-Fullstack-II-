package com.hospitaltech_cl.habitaciones.controller;

import com.hospitaltech_cl.habitaciones.model.Habitacion;
import com.hospitaltech_cl.habitaciones.service.HabitacionService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/habitaciones")
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
public class HabitacionController {

    @Autowired
    private HabitacionService habitacionService;

    /**
     * Crear nueva habitación
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> crearHabitacion(@Valid @RequestBody Habitacion habitacion) {
        try {
            log.info("Solicitud para crear nueva habitación");
            Habitacion nuevaHabitacion = habitacionService.crearHabitacion(habitacion);
            
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Habitación creada exitosamente");
            response.put("habitacion", nuevaHabitacion);
            response.put("status", HttpStatus.CREATED.value());
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            log.error("Error al crear habitación: {}", e.getMessage(), e);
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            error.put("status", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    /**
     * Obtener habitación por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> obtenerHabitacion(@PathVariable Long id) {
        try {
            Optional<Habitacion> habitacion = habitacionService.obtenerHabitacionById(id);
            
            if (habitacion.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("habitacion", habitacion.get());
                response.put("status", HttpStatus.OK.value());
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> error = new HashMap<>();
                error.put("error", "Habitación no encontrada");
                error.put("status", HttpStatus.NOT_FOUND.value());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
        } catch (Exception e) {
            log.error("Error al obtener habitación: {}", e.getMessage(), e);
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Obtener habitación por número
     */
    @GetMapping("/numero/{numeroHabitacion}")
    public ResponseEntity<Map<String, Object>> obtenerHabitacionPorNumero(@PathVariable String numeroHabitacion) {
        try {
            Optional<Habitacion> habitacion = habitacionService.obtenerHabitacionByNumero(numeroHabitacion);
            
            if (habitacion.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("habitacion", habitacion.get());
                response.put("status", HttpStatus.OK.value());
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> error = new HashMap<>();
                error.put("error", "Habitación no encontrada");
                error.put("status", HttpStatus.NOT_FOUND.value());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
        } catch (Exception e) {
            log.error("Error al obtener habitación: {}", e.getMessage(), e);
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Obtener todas las habitaciones
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> obtenerTodasLasHabitaciones() {
        try {
            List<Habitacion> habitaciones = habitacionService.obtenerTodasLasHabitaciones();
            
            Map<String, Object> response = new HashMap<>();
            response.put("habitaciones", habitaciones);
            response.put("total", habitaciones.size());
            response.put("status", HttpStatus.OK.value());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error al obtener habitaciones: {}", e.getMessage(), e);
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Obtener habitaciones disponibles
     */
    @GetMapping("/disponibles")
    public ResponseEntity<Map<String, Object>> obtenerHabitacionesDisponibles() {
        try {
            List<Habitacion> habitaciones = habitacionService.obtenerHabitacionesDisponibles();
            
            Map<String, Object> response = new HashMap<>();
            response.put("habitaciones", habitaciones);
            response.put("total", habitaciones.size());
            response.put("status", HttpStatus.OK.value());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error al obtener habitaciones disponibles: {}", e.getMessage(), e);
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Obtener habitaciones por estado
     */
    @GetMapping("/estado/{estado}")
    public ResponseEntity<Map<String, Object>> obtenerHabitacionesPorEstado(@PathVariable String estado) {
        try {
            Habitacion.EstadoHabitacion estadoEnum = Habitacion.EstadoHabitacion.valueOf(estado.toUpperCase());
            List<Habitacion> habitaciones = habitacionService.obtenerHabitacionesPorEstado(estadoEnum);
            
            Map<String, Object> response = new HashMap<>();
            response.put("habitaciones", habitaciones);
            response.put("total", habitaciones.size());
            response.put("status", HttpStatus.OK.value());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            log.error("Estado inválido: {}", estado);
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Estado de habitación inválido");
            error.put("status", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (Exception e) {
            log.error("Error al obtener habitaciones por estado: {}", e.getMessage(), e);
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Obtener habitaciones por tipo
     */
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<Map<String, Object>> obtenerHabitacionesPorTipo(@PathVariable String tipo) {
        try {
            Habitacion.TipoHabitacion tipoEnum = Habitacion.TipoHabitacion.valueOf(tipo.toUpperCase());
            List<Habitacion> habitaciones = habitacionService.obtenerHabitacionesPorTipo(tipoEnum);
            
            Map<String, Object> response = new HashMap<>();
            response.put("habitaciones", habitaciones);
            response.put("total", habitaciones.size());
            response.put("status", HttpStatus.OK.value());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            log.error("Tipo inválido: {}", tipo);
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Tipo de habitación inválido");
            error.put("status", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (Exception e) {
            log.error("Error al obtener habitaciones por tipo: {}", e.getMessage(), e);
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Obtener habitaciones por piso
     */
    @GetMapping("/piso/{piso}")
    public ResponseEntity<Map<String, Object>> obtenerHabitacionesPorPiso(@PathVariable Integer piso) {
        try {
            List<Habitacion> habitaciones = habitacionService.obtenerHabitacionesPorPiso(piso);
            
            Map<String, Object> response = new HashMap<>();
            response.put("habitaciones", habitaciones);
            response.put("total", habitaciones.size());
            response.put("status", HttpStatus.OK.value());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error al obtener habitaciones por piso: {}", e.getMessage(), e);
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Actualizar habitación
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizarHabitacion(@PathVariable Long id, @Valid @RequestBody Habitacion habitacion) {
        try {
            Habitacion habitacionActualizada = habitacionService.actualizarHabitacion(id, habitacion);
            
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Habitación actualizada exitosamente");
            response.put("habitacion", habitacionActualizada);
            response.put("status", HttpStatus.OK.value());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            log.error("Error al actualizar habitación: {}", e.getMessage(), e);
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            error.put("status", HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            log.error("Error inesperado al actualizar habitación: {}", e.getMessage(), e);
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Cambiar estado de habitación
     */
    @PatchMapping("/{id}/estado")
    public ResponseEntity<Map<String, Object>> cambiarEstado(@PathVariable Long id, @RequestParam String estado) {
        try {
            Habitacion.EstadoHabitacion estadoEnum = Habitacion.EstadoHabitacion.valueOf(estado.toUpperCase());
            Habitacion habitacionActualizada = habitacionService.cambiarEstado(id, estadoEnum);
            
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Estado de habitación actualizado");
            response.put("habitacion", habitacionActualizada);
            response.put("status", HttpStatus.OK.value());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            log.error("Estado inválido: {}", estado);
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Estado inválido");
            error.put("status", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (RuntimeException e) {
            log.error("Error al cambiar estado: {}", e.getMessage(), e);
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            error.put("status", HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            log.error("Error inesperado: {}", e.getMessage(), e);
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Asignar paciente a habitación
     */
    @PostMapping("/{id}/asignar-paciente")
    public ResponseEntity<Map<String, Object>> asignarPaciente(@PathVariable Long id, @RequestParam Long idPaciente) {
        try {
            Habitacion habitacionActualizada = habitacionService.asignarPaciente(id, idPaciente);
            
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Paciente asignado exitosamente");
            response.put("habitacion", habitacionActualizada);
            response.put("status", HttpStatus.OK.value());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            log.error("Error al asignar paciente: {}", e.getMessage(), e);
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            error.put("status", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (Exception e) {
            log.error("Error inesperado: {}", e.getMessage(), e);
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Liberar habitación
     */
    @PostMapping("/{id}/liberar")
    public ResponseEntity<Map<String, Object>> liberarHabitacion(@PathVariable Long id) {
        try {
            Habitacion habitacionActualizada = habitacionService.liberarHabitacion(id);
            
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Habitación liberada exitosamente");
            response.put("habitacion", habitacionActualizada);
            response.put("status", HttpStatus.OK.value());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            log.error("Error al liberar habitación: {}", e.getMessage(), e);
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            error.put("status", HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            log.error("Error inesperado: {}", e.getMessage(), e);
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Eliminar habitación
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminarHabitacion(@PathVariable Long id) {
        try {
            habitacionService.eliminarHabitacion(id);
            
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Habitación eliminada exitosamente");
            response.put("status", HttpStatus.OK.value());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            log.error("Error al eliminar habitación: {}", e.getMessage(), e);
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            error.put("status", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (Exception e) {
            log.error("Error inesperado: {}", e.getMessage(), e);
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Obtener estadísticas de habitaciones
     */
    @GetMapping("/estadisticas/resumen")
    public ResponseEntity<Map<String, Object>> obtenerEstadisticas() {
        try {
            Map<String, Object> estadisticas = new HashMap<>();
            estadisticas.put("disponibles", habitacionService.contarHabitacionesDisponibles());
            estadisticas.put("ocupadas", habitacionService.contarHabitacionesOcupadas());
            estadisticas.put("mantenimiento", habitacionService.contarHabitacionesEnMantenimiento());
            estadisticas.put("total", habitacionService.obtenerTodasLasHabitaciones().size());
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

    /**
     * Obtener habitaciones de un paciente
     */
    @GetMapping("/paciente/{idPaciente}")
    public ResponseEntity<Map<String, Object>> obtenerHabitacionesPorPaciente(@PathVariable Long idPaciente) {
        try {
            List<Habitacion> habitaciones = habitacionService.obtenerHabitacionesPorPaciente(idPaciente);
            
            Map<String, Object> response = new HashMap<>();
            response.put("habitaciones", habitaciones);
            response.put("total", habitaciones.size());
            response.put("status", HttpStatus.OK.value());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error al obtener habitaciones del paciente: {}", e.getMessage(), e);
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
