package com.hospitaltech_cl.paciente.repository;

import com.hospitaltech_cl.paciente.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
}