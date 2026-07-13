package com.hospitaltech_cl.inventario.controller;

import com.hospitaltech_cl.inventario.model.Inventario;
import com.hospitaltech_cl.inventario.service.InventarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/inventario")
@Tag(name = "Inventario", description = "Gestión del inventario del hospital")
public class InventarioController {

    private final InventarioService service;

    public InventarioController(InventarioService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Crear registro de inventario", description = "Registra un nuevo artículo en el inventario")
    @ApiResponse(responseCode = "200", description = "Registro creado exitosamente")
    public Inventario create(@RequestBody Inventario inventario) {
        return service.save(inventario);
    }

    @GetMapping
    @Operation(summary = "Obtener todo el inventario", description = "Retorna la lista completa de artículos en inventario")
    @ApiResponse(responseCode = "200", description = "Lista obtenida")
    public List<Inventario> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener artículo por ID", description = "Busca un artículo específico del inventario")
    @ApiResponse(responseCode = "200", description = "Artículo encontrado")
    public Optional<Inventario> getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar artículo", description = "Modifica los datos de un artículo del inventario")
    @ApiResponse(responseCode = "200", description = "Artículo actualizado exitosamente")
    public Inventario update(@PathVariable Long id, @RequestBody Inventario inventario) {
        return service.update(id, inventario);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar artículo", description = "Borra un artículo del inventario")
    @ApiResponse(responseCode = "200", description = "Artículo eliminado correctamente")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Registro de inventario eliminado";
    }
}