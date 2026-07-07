package com.hospitaltech_cl.habitaciones.repository;

import com.hospitaltech_cl.habitaciones.model.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {

    Optional<Habitacion> findByNumeroHabitacion(String numeroHabitacion);

    List<Habitacion> findByEstado(Habitacion.EstadoHabitacion estado);

    List<Habitacion> findByTipo(Habitacion.TipoHabitacion tipo);

    List<Habitacion> findByPiso(Integer piso);

    @Query("SELECT h FROM Habitacion h WHERE h.estado = 'DISPONIBLE'")
    List<Habitacion> findHabitacionesDisponibles();

    @Query("SELECT h FROM Habitacion h WHERE h.estado = 'OCUPADA' AND h.idPacienteActual = :idPaciente")
    List<Habitacion> findHabitacionesPorPaciente(@Param("idPaciente") Long idPaciente);

    @Query("SELECT COUNT(h) FROM Habitacion h WHERE h.estado = 'DISPONIBLE'")
    long countDisponibles();

    @Query("SELECT COUNT(h) FROM Habitacion h WHERE h.estado = 'OCUPADA'")
    long countOcupadas();

    @Query("SELECT COUNT(h) FROM Habitacion h WHERE h.estado = 'MANTENIMIENTO'")
    long countMantenimiento();
}
