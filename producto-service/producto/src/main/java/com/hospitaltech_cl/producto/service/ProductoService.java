package com.hospitaltech_cl.producto.service;

import com.hospitaltech_cl.producto.model.Producto;
import com.hospitaltech_cl.producto.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository repository;

    public ProductoService(ProductoRepository repository) {
        this.repository = repository;
    }

    public List<Producto> getAll() {
        return repository.findAll();
    }

    public Optional<Producto> getById(Long id) {
        return repository.findById(id);
    }

    public Producto save(Producto producto) {
        return repository.save(producto);
    }

    public Producto update(Long id, Producto producto) {
        producto.setId(id);
        return repository.save(producto);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}