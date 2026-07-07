package com.hospitaltech_cl.examen.service;

import com.hospitaltech_cl.examen.model.Examen;
import com.hospitaltech_cl.examen.repository.ExamenRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExamenService {

    private final ExamenRepository repository;

    public ExamenService(ExamenRepository repository) {
        this.repository = repository;
    }

    public List<Examen> getAll() {
        return repository.findAll();
    }

    public Optional<Examen> getById(Long id) {
        return repository.findById(id);
    }

    public Examen save(Examen examen) {
        return repository.save(examen);
    }

    public Examen update(Long id, Examen examen) {
        examen.setId(id);
        return repository.save(examen);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}