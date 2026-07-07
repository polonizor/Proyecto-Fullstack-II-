package com.hospitaltech_cl.medico.controller;

import com.hospitaltech_cl.medico.model.Medico;
import com.hospitaltech_cl.medico.service.MedicoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    private final MedicoService medicoService;

    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    @PostMapping
    public Medico create(@RequestBody Medico medico) {
        return medicoService.saveMedico(medico);
    }

    @GetMapping
    public List<Medico> getAll() {
        return medicoService.getAllMedicos();
    }

    @GetMapping("/{id}")
    public Optional<Medico> getById(@PathVariable Long id) {
        return medicoService.getMedicoById(id);
    }

    @PutMapping("/{id}")
    public Medico update(@PathVariable Long id, @RequestBody Medico medico) {
        return medicoService.updateMedico(id, medico);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        medicoService.deleteMedico(id);
        return "Médico eliminado correctamente";
    }
}