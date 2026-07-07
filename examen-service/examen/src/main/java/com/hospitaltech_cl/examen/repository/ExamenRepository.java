package com.hospitaltech_cl.examen.repository;

import com.hospitaltech_cl.examen.model.Examen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamenRepository extends JpaRepository<Examen, Long> {
}