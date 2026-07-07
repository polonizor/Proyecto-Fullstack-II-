package com.hospitaltech_cl.especialidad.service;

import com.hospitaltech_cl.especialidad.model.Especialidad;
import com.hospitaltech_cl.especialidad.repository.EspecialidadRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EspecialidadService {

    private final EspecialidadRepository repository;

    public EspecialidadService(EspecialidadRepository repository) {
        this.repository = repository;
    }

    public List<Especialidad> getAll() {
        return repository.findAll();
    }

    public Optional<Especialidad> getById(Long id) {
        return repository.findById(id);
    }

    public Especialidad save(Especialidad especialidad) {
        return repository.save(especialidad);
    }

    public Especialidad update(Long id, Especialidad especialidad) {
        especialidad.setId(id);
        return repository.save(especialidad);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}