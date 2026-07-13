package com.hospitaltech_cl.especialidad.controller;

import com.hospitaltech_cl.especialidad.model.Especialidad;
import com.hospitaltech_cl.especialidad.service.EspecialidadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/especialidades")
@Tag(name = "Especialidades", description = "Gestión de especialidades médicas")
public class EspecialidadController {

    private final EspecialidadService service;

    public EspecialidadController(EspecialidadService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Crear nueva especialidad", description = "Registra una nueva especialidad médica")
    @ApiResponse(responseCode = "200", description = "Especialidad creada exitosamente")
    public Especialidad create(@RequestBody Especialidad especialidad) {
        return service.save(especialidad);
    }

    @GetMapping
    @Operation(summary = "Obtener todas las especialidades", description = "Retorna la lista completa de especialidades")
    @ApiResponse(responseCode = "200", description = "Lista de especialidades obtenida")
    public List<Especialidad> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener especialidad por ID", description = "Busca una especialidad específica por su ID")
    @ApiResponse(responseCode = "200", description = "Especialidad encontrada")
    public Optional<Especialidad> getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar especialidad", description = "Modifica los datos de una especialidad existente")
    @ApiResponse(responseCode = "200", description = "Especialidad actualizada exitosamente")
    public Especialidad update(@PathVariable Long id,
                               @RequestBody Especialidad especialidad) {
        return service.update(id, especialidad);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar especialidad", description = "Borra una especialidad del sistema")
    @ApiResponse(responseCode = "200", description = "Especialidad eliminada correctamente")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Especialidad eliminada";
    }
}