package com.hospitaltech_cl.producto.repository;

import com.hospitaltech_cl.producto.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}