package com.hospitaltech_cl.cita.service;

import com.hospitaltech_cl.cita.model.Cita;
import com.hospitaltech_cl.cita.repository.CitaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CitaService {

    private final CitaRepository repository;

    public CitaService(CitaRepository repository) {
        this.repository = repository;
    }

    public List<Cita> getAll() {
        return repository.findAll();
    }

    public Optional<Cita> getById(Long id) {
        return repository.findById(id);
    }

    public Cita save(Cita cita) {
        return repository.save(cita);
    }

    public Cita update(Long id, Cita cita) {
        cita.setId(id);
        return repository.save(cita);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}