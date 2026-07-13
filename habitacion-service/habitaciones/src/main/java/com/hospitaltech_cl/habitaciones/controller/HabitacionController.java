package com.hospitaltech_cl.habitaciones.controller;

import com.hospitaltech_cl.habitaciones.model.Habitacion;
import com.hospitaltech_cl.habitaciones.service.HabitacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Habitaciones", description = "Gestión de habitaciones del hospital")
public class HabitacionController {

    @Autowired
    private HabitacionService habitacionService;

    @PostMapping
    @Operation(summary = "Crear nueva habitación", description = "Registra una nueva habitación en el sistema")
    @ApiResponse(responseCode = "201", description = "Habitación creada exitosamente")
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

    @GetMapping("/{id}")
    @Operation(summary = "Obtener habitación por ID", description = "Busca una habitación específica por su ID")
    @ApiResponse(responseCode = "200", description = "Habitación encontrada")
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

    @GetMapping("/numero/{numeroHabitacion}")
    @Operation(summary = "Obtener habitación por número", description = "Busca una habitación por su número")
    @ApiResponse(responseCode = "200", description = "Habitación encontrada")
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

    @GetMapping
    @Operation(summary = "Obtener todas las habitaciones", description = "Retorna la lista completa de habitaciones")
    @ApiResponse(responseCode = "200", description = "Lista de habitaciones obtenida")
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

    @GetMapping("/disponibles")
    @Operation(summary = "Obtener habitaciones disponibles", description = "Retorna todas las habitaciones disponibles para asignar")
    @ApiResponse(responseCode = "200", description = "Habitaciones disponibles obtenidas")
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

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Obtener habitaciones por estado", description = "Filtra habitaciones por estado (DISPONIBLE, OCUPADA, MANTENIMIENTO)")
    @ApiResponse(responseCode = "200", description = "Habitaciones filtradas")
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

    @GetMapping("/tipo/{tipo}")
    @Operation(summary = "Obtener habitaciones por tipo", description = "Filtra habitaciones por tipo (INDIVIDUAL, DOBLE, SUITE)")
    @ApiResponse(responseCode = "200", description = "Habitaciones filtradas")
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

    @GetMapping("/piso/{piso}")
    @Operation(summary = "Obtener habitaciones por piso", description = "Filtra habitaciones por número de piso")
    @ApiResponse(responseCode = "200", description = "Habitaciones filtradas")
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

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar habitación", description = "Modifica los datos de una habitación existente")
    @ApiResponse(responseCode = "200", description = "Habitación actualizada")
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

    @PatchMapping("/{id}/estado")
    @Operation(summary = "Cambiar estado de habitación", description = "Actualiza el estado de una habitación")
    @ApiResponse(responseCode = "200", description = "Estado actualizado")
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

    @PostMapping("/{id}/asignar-paciente")
    @Operation(summary = "Asignar paciente a habitación", description = "Vincula un paciente a una habitación")
    @ApiResponse(responseCode = "200", description = "Paciente asignado")
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

    @PostMapping("/{id}/liberar")
    @Operation(summary = "Liberar habitación", description = "Marca una habitación como disponible nuevamente")
    @ApiResponse(responseCode = "200", description = "Habitación liberada")
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

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar habitación", description = "Borra una habitación del sistema")
    @ApiResponse(responseCode = "200", description = "Habitación eliminada")
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

    @GetMapping("/estadisticas/resumen")
    @Operation(summary = "Obtener estadísticas de habitaciones", description = "Retorna estadísticas sobre disponibilidad y ocupación")
    @ApiResponse(responseCode = "200", description = "Estadísticas obtenidas")
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

    @GetMapping("/paciente/{idPaciente}")
    @Operation(summary = "Obtener habitaciones de paciente", description = "Retorna las habitaciones asignadas a un paciente")
    @ApiResponse(responseCode = "200", description = "Habitaciones obtenidas")
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
