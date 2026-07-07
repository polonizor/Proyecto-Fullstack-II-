package com.hospitaltech_cl.especialidad.controller;

import com.hospitaltech_cl.especialidad.model.Especialidad;
import com.hospitaltech_cl.especialidad.service.EspecialidadService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/especialidades")
public class EspecialidadController {

    private final EspecialidadService service;

    public EspecialidadController(EspecialidadService service) {
        this.service = service;
    }

    @PostMapping
    public Especialidad create(@RequestBody Especialidad especialidad) {
        return service.save(especialidad);
    }

    @GetMapping
    public List<Especialidad> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Especialidad> getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public Especialidad update(@PathVariable Long id,
                               @RequestBody Especialidad especialidad) {
        return service.update(id, especialidad);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Especialidad eliminada";
    }
}