package com.hospitaltech_cl.producto.controller;

import com.hospitaltech_cl.producto.model.Producto;
import com.hospitaltech_cl.producto.service.ProductoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @PostMapping
    public Producto create(@RequestBody Producto producto) {
        return service.save(producto);
    }

    @GetMapping
    public List<Producto> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Producto> getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public Producto update(@PathVariable Long id, @RequestBody Producto producto) {
        return service.update(id, producto);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Producto eliminado";
    }
}