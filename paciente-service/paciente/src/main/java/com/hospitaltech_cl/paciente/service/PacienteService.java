package com.hospitaltech_cl.paciente.service;

import com.hospitaltech_cl.paciente.model.Paciente;
import com.hospitaltech_cl.paciente.repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    private final PacienteRepository repository;

    public PacienteService(PacienteRepository repository) {
        this.repository = repository;
    }

    public List<Paciente> listar() {
        return repository.findAll();
    }

    public Optional<Paciente> buscar(Long id) {
        return repository.findById(id);
    }

    public Paciente guardar(Paciente paciente) {
        return repository.save(paciente);
    }

    public Paciente actualizar(Long id, Paciente paciente) {
        paciente.setId(id);
        return repository.save(paciente);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}