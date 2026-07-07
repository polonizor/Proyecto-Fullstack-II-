package com.hospitaltech_cl.cita.controller;

import com.hospitaltech_cl.cita.model.Cita;
import com.hospitaltech_cl.cita.service.CitaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/citas")
public class CitaController {

    private final CitaService service;

    public CitaController(CitaService service) {
        this.service = service;
    }

    @PostMapping
    public Cita create(@RequestBody Cita cita) {
        return service.save(cita);
    }

    @GetMapping
    public List<Cita> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Cita> getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public Cita update(@PathVariable Long id, @RequestBody Cita cita) {
        return service.update(id, cita);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Cita eliminada";
    }
}