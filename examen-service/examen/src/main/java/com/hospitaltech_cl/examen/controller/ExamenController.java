package com.hospitaltech_cl.examen.controller;

import com.hospitaltech_cl.examen.model.Examen;
import com.hospitaltech_cl.examen.service.ExamenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/examenes")
@Tag(name = "Exámenes", description = "Gestión de exámenes médicos")
public class ExamenController {

    private final ExamenService service;

    public ExamenController(ExamenService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Crear nuevo examen", description = "Registra un nuevo examen en el sistema")
    @ApiResponse(responseCode = "200", description = "Examen creado exitosamente")
    public Examen create(@RequestBody Examen examen) {
        return service.save(examen);
    }

    @GetMapping
    @Operation(summary = "Obtener todos los exámenes", description = "Retorna la lista completa de exámenes")
    @ApiResponse(responseCode = "200", description = "Lista de exámenes obtenida")
    public List<Examen> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener examen por ID", description = "Busca un examen específico por su ID")
    @ApiResponse(responseCode = "200", description = "Examen encontrado")
    public Optional<Examen> getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar examen", description = "Modifica los datos de un examen existente")
    @ApiResponse(responseCode = "200", description = "Examen actualizado exitosamente")
    public Examen update(@PathVariable Long id, @RequestBody Examen examen) {
        return service.update(id, examen);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar examen", description = "Borra un examen del sistema")
    @ApiResponse(responseCode = "200", description = "Examen eliminado correctamente")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Examen eliminado";
    }
}