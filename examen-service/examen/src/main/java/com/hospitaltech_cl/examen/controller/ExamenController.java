package com.hospitaltech_cl.examen.controller;

import com.hospitaltech_cl.examen.model.Examen;
import com.hospitaltech_cl.examen.service.ExamenService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/examenes")
public class ExamenController {

    private final ExamenService service;

    public ExamenController(ExamenService service) {
        this.service = service;
    }

    @PostMapping
    public Examen create(@RequestBody Examen examen) {
        return service.save(examen);
    }

    @GetMapping
    public List<Examen> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Examen> getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public Examen update(@PathVariable Long id, @RequestBody Examen examen) {
        return service.update(id, examen);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Examen eliminado";
    }
}