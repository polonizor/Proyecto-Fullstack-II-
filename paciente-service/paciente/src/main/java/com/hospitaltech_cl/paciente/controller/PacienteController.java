package com.hospitaltech_cl.paciente.controller;

import com.hospitaltech_cl.paciente.model.Paciente;
import com.hospitaltech_cl.paciente.service.PacienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
@Tag(name = "Pacientes", description = "Gestión de pacientes del hospital")
public class PacienteController {

    private final PacienteService service;

    public PacienteController(PacienteService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar todos los pacientes", description = "Obtiene la lista completa de pacientes")
    @ApiResponse(responseCode = "200", description = "Lista de pacientes obtenida exitosamente")
    public List<Paciente> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener paciente por ID", description = "Busca un paciente específico por su ID")
    @ApiResponse(responseCode = "200", description = "Paciente encontrado")
    public Optional<Paciente> buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PostMapping
    @Operation(summary = "Crear nuevo paciente", description = "Registra un nuevo paciente en el sistema")
    @ApiResponse(responseCode = "200", description = "Paciente creado exitosamente")
    public Paciente guardar(@RequestBody Paciente paciente) {
        return service.guardar(paciente);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar paciente", description = "Modifica los datos de un paciente existente")
    @ApiResponse(responseCode = "200", description = "Paciente actualizado exitosamente")
    public Paciente actualizar(@PathVariable Long id, @RequestBody Paciente paciente) {
        return service.actualizar(id, paciente);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar paciente", description = "Borra un paciente del sistema")
    @ApiResponse(responseCode = "200", description = "Paciente eliminado correctamente")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}