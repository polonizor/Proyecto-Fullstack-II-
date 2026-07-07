package com.hospitaltech_cl.inventario.controller;

import com.hospitaltech_cl.inventario.model.Inventario;
import com.hospitaltech_cl.inventario.service.InventarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/inventario")
public class InventarioController {

    private final InventarioService service;

    public InventarioController(InventarioService service) {
        this.service = service;
    }

    @PostMapping
    public Inventario create(@RequestBody Inventario inventario) {
        return service.save(inventario);
    }

    @GetMapping
    public List<Inventario> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Inventario> getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public Inventario update(@PathVariable Long id, @RequestBody Inventario inventario) {
        return service.update(id, inventario);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Registro de inventario eliminado";
    }
}