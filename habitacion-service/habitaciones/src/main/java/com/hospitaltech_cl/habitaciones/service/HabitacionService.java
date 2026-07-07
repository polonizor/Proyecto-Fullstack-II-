package com.hospitaltech_cl.habitaciones.service;

import com.hospitaltech_cl.habitaciones.model.Habitacion;
import com.hospitaltech_cl.habitaciones.repository.HabitacionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class HabitacionService {

    @Autowired
    private HabitacionRepository habitacionRepository;

    @Transactional
    public Habitacion crearHabitacion(Habitacion habitacion) {
        log.info("Creando nueva habitación: {}", habitacion.getNumeroHabitacion());
        return habitacionRepository.save(habitacion);
    }

    @Transactional(readOnly = true)
    public Optional<Habitacion> obtenerHabitacionById(Long id) {
        log.info("Obteniendo habitación con ID: {}", id);
        return habitacionRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Habitacion> obtenerHabitacionByNumero(String numeroHabitacion) {
        log.info("Obteniendo habitación: {}", numeroHabitacion);
        return habitacionRepository.findByNumeroHabitacion(numeroHabitacion);
    }

    @Transactional(readOnly = true)
    public List<Habitacion> obtenerTodasLasHabitaciones() {
        log.info("Obteniendo todas las habitaciones");
        return habitacionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Habitacion> obtenerHabitacionesPorEstado(Habitacion.EstadoHabitacion estado) {
        log.info("Obteniendo habitaciones con estado: {}", estado);
        return habitacionRepository.findByEstado(estado);
    }

    @Transactional(readOnly = true)
    public List<Habitacion> obtenerHabitacionesPorTipo(Habitacion.TipoHabitacion tipo) {
        log.info("Obteniendo habitaciones de tipo: {}", tipo);
        return habitacionRepository.findByTipo(tipo);
    }

    @Transactional(readOnly = true)
    public List<Habitacion> obtenerHabitacionesPorPiso(Integer piso) {
        log.info("Obteniendo habitaciones del piso: {}", piso);
        return habitacionRepository.findByPiso(piso);
    }

    @Transactional(readOnly = true)
    public List<Habitacion> obtenerHabitacionesDisponibles() {
        log.info("Obteniendo habitaciones disponibles");
        return habitacionRepository.findHabitacionesDisponibles();
    }

    @Transactional
    public Habitacion actualizarHabitacion(Long id, Habitacion habitacionActualizada) {
        log.info("Actualizando habitación con ID: {}", id);
        
        return habitacionRepository.findById(id).map(habitacion -> {
            habitacion.setNumeroHabitacion(habitacionActualizada.getNumeroHabitacion());
            habitacion.setTipo(habitacionActualizada.getTipo());
            habitacion.setPiso(habitacionActualizada.getPiso());
            habitacion.setCapacidad(habitacionActualizada.getCapacidad());
            habitacion.setPrecioPorNoche(habitacionActualizada.getPrecioPorNoche());
            habitacion.setDescripcion(habitacionActualizada.getDescripcion());
            habitacion.setTieneBanio(habitacionActualizada.getTieneBanio());
            habitacion.setTieneAire(habitacionActualizada.getTieneAire());
            habitacion.setTieneTelevisor(habitacionActualizada.getTieneTelevisor());
            habitacion.setTieneWifi(habitacionActualizada.getTieneWifi());
            
            return habitacionRepository.save(habitacion);
        }).orElseThrow(() -> new RuntimeException("Habitación no encontrada con ID: " + id));
    }

    @Transactional
    public Habitacion cambiarEstado(Long id, Habitacion.EstadoHabitacion nuevoEstado) {
        log.info("Cambiando estado de habitación ID: {} a {}", id, nuevoEstado);
        
        return habitacionRepository.findById(id).map(habitacion -> {
            habitacion.setEstado(nuevoEstado);
            return habitacionRepository.save(habitacion);
        }).orElseThrow(() -> new RuntimeException("Habitación no encontrada con ID: " + id));
    }

    @Transactional
    public Habitacion asignarPaciente(Long id, Long idPaciente) {
        log.info("Asignando paciente {} a habitación {}", idPaciente, id);
        
        return habitacionRepository.findById(id).map(habitacion -> {
            if (habitacion.getEstado() != Habitacion.EstadoHabitacion.DISPONIBLE) {
                throw new RuntimeException("La habitación no está disponible");
            }
            
            habitacion.setIdPacienteActual(idPaciente);
            habitacion.setEstado(Habitacion.EstadoHabitacion.OCUPADA);
            return habitacionRepository.save(habitacion);
        }).orElseThrow(() -> new RuntimeException("Habitación no encontrada con ID: " + id));
    }

    @Transactional
    public Habitacion liberarHabitacion(Long id) {
        log.info("Liberando habitación con ID: {}", id);
        
        return habitacionRepository.findById(id).map(habitacion -> {
            habitacion.setIdPacienteActual(null);
            habitacion.setEstado(Habitacion.EstadoHabitacion.DISPONIBLE);
            return habitacionRepository.save(habitacion);
        }).orElseThrow(() -> new RuntimeException("Habitación no encontrada con ID: " + id));
    }

    @Transactional
    public void eliminarHabitacion(Long id) {
        log.info("Eliminando habitación con ID: {}", id);
        
        Habitacion habitacion = habitacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Habitación no encontrada con ID: " + id));
        
        if (habitacion.getEstado() == Habitacion.EstadoHabitacion.OCUPADA) {
            throw new RuntimeException("No se puede eliminar una habitación ocupada");
        }
        
        habitacionRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public long contarHabitacionesDisponibles() {
        log.info("Contando habitaciones disponibles");
        return habitacionRepository.countDisponibles();
    }

    @Transactional(readOnly = true)
    public long contarHabitacionesOcupadas() {
        log.info("Contando habitaciones ocupadas");
        return habitacionRepository.countOcupadas();
    }

    @Transactional(readOnly = true)
    public long contarHabitacionesEnMantenimiento() {
        log.info("Contando habitaciones en mantenimiento");
        return habitacionRepository.countMantenimiento();
    }

    @Transactional(readOnly = true)
    public List<Habitacion> obtenerHabitacionesPorPaciente(Long idPaciente) {
        log.info("Obteniendo habitaciones del paciente: {}", idPaciente);
        return habitacionRepository.findHabitacionesPorPaciente(idPaciente);
    }
}
