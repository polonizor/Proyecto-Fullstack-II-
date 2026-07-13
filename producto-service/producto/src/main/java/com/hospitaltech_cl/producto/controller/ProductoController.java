package com.hospitaltech_cl.producto.controller;

import com.hospitaltech_cl.producto.model.Producto;
import com.hospitaltech_cl.producto.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productos")
@Tag(name = "Productos", description = "Gestión de productos del hospital")
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Crear nuevo producto", description = "Registra un nuevo producto en el sistema")
    @ApiResponse(responseCode = "200", description = "Producto creado exitosamente")
    public Producto create(@RequestBody Producto producto) {
        return service.save(producto);
    }

    @GetMapping
    @Operation(summary = "Obtener todos los productos", description = "Retorna la lista completa de productos")
    @ApiResponse(responseCode = "200", description = "Lista de productos obtenida")
    public List<Producto> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID", description = "Busca un producto específico por su ID")
    @ApiResponse(responseCode = "200", description = "Producto encontrado")
    public Optional<Producto> getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar producto", description = "Modifica los datos de un producto existente")
    @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente")
    public Producto update(@PathVariable Long id, @RequestBody Producto producto) {
        return service.update(id, producto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar producto", description = "Borra un producto del sistema")
    @ApiResponse(responseCode = "200", description = "Producto eliminado correctamente")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Producto eliminado";
    }
}