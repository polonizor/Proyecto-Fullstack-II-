package com.hospitaltech_cl.inventario.repository;

import com.hospitaltech_cl.inventario.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventarioRepository extends JpaRepository<Inventario, Long> {
}