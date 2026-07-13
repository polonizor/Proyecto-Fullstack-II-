package com.hospitaltech_cl.cita.controller;

import com.hospitaltech_cl.cita.model.Cita;
import com.hospitaltech_cl.cita.service.CitaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/citas")
@Tag(name = "Citas", description = "Gestión de citas médicas")
public class CitaController {

    private final CitaService service;

    public CitaController(CitaService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Crear nueva cita", description = "Registra una nueva cita en el sistema")
    @ApiResponse(responseCode = "200", description = "Cita creada exitosamente")
    public Cita create(@RequestBody Cita cita) {
        return service.save(cita);
    }

    @GetMapping
    @Operation(summary = "Obtener todas las citas", description = "Retorna la lista completa de citas")
    @ApiResponse(responseCode = "200", description = "Lista de citas obtenida")
    public List<Cita> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener cita por ID", description = "Busca una cita específica por su ID")
    @ApiResponse(responseCode = "200", description = "Cita encontrada")
    public Optional<Cita> getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar cita", description = "Modifica los datos de una cita existente")
    @ApiResponse(responseCode = "200", description = "Cita actualizada exitosamente")
    public Cita update(@PathVariable Long id, @RequestBody Cita cita) {
        return service.update(id, cita);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar cita", description = "Borra una cita del sistema")
    @ApiResponse(responseCode = "200", description = "Cita eliminada correctamente")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Cita eliminada";
    }
}