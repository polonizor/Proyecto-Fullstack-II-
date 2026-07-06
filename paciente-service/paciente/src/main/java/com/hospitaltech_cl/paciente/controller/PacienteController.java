package com.hospitaltech_cl.paciente.controller;

import com.hospitaltech_cl.paciente.model.Paciente;
import com.hospitaltech_cl.paciente.service.PacienteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private final PacienteService service;

    public PacienteController(PacienteService service) {
        this.service = service;
    }

    @GetMapping
    public List<Paciente> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Optional<Paciente> buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PostMapping
    public Paciente guardar(@RequestBody Paciente paciente) {
        return service.guardar(paciente);
    }

    @PutMapping("/{id}")
    public Paciente actualizar(@PathVariable Long id, @RequestBody Paciente paciente) {
        return service.actualizar(id, paciente);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}