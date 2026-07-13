package com.hospitaltech_cl.medico.controller;

import com.hospitaltech_cl.medico.model.Medico;
import com.hospitaltech_cl.medico.service.MedicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/medicos")
@Tag(name = "Médicos", description = "Gestión de médicos del hospital")
public class MedicoController {

    private final MedicoService medicoService;

    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    @PostMapping
    @Operation(summary = "Crear nuevo médico", description = "Registra un nuevo médico en el sistema")
    @ApiResponse(responseCode = "200", description = "Médico creado exitosamente")
    public Medico create(@RequestBody Medico medico) {
        return medicoService.saveMedico(medico);
    }

    @GetMapping
    @Operation(summary = "Obtener todos los médicos", description = "Retorna la lista completa de médicos")
    @ApiResponse(responseCode = "200", description = "Lista de médicos obtenida")
    public List<Medico> getAll() {
        return medicoService.getAllMedicos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener médico por ID", description = "Busca un médico específico por su ID")
    @ApiResponse(responseCode = "200", description = "Médico encontrado")
    public Optional<Medico> getById(@PathVariable Long id) {
        return medicoService.getMedicoById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar médico", description = "Modifica los datos de un médico existente")
    @ApiResponse(responseCode = "200", description = "Médico actualizado exitosamente")
    public Medico update(@PathVariable Long id, @RequestBody Medico medico) {
        return medicoService.updateMedico(id, medico);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar médico", description = "Borra un médico del sistema")
    @ApiResponse(responseCode = "200", description = "Médico eliminado correctamente")
    public String delete(@PathVariable Long id) {
        medicoService.deleteMedico(id);
        return "Médico eliminado correctamente";
    }
}