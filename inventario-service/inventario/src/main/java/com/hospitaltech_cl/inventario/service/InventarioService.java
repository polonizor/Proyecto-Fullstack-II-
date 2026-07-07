package com.hospitaltech_cl.inventario.service;

import com.hospitaltech_cl.inventario.model.Inventario;
import com.hospitaltech_cl.inventario.repository.InventarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventarioService {

    private final InventarioRepository repository;

    public InventarioService(InventarioRepository repository) {
        this.repository = repository;
    }

    public List<Inventario> getAll() {
        return repository.findAll();
    }

    public Optional<Inventario> getById(Long id) {
        return repository.findById(id);
    }

    public Inventario save(Inventario inventario) {
        return repository.save(inventario);
    }

    public Inventario update(Long id, Inventario inventario) {
        inventario.setId(id);
        return repository.save(inventario);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}