package com.evaluacion3.spaceti_api.controller;

import com.evaluacion3.spaceti_api.exception.ResourceNotFoundException;
import com.evaluacion3.spaceti_api.model.Producto;
import com.evaluacion3.spaceti_api.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
@RequiredArgsConstructor
@Slf4j
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<Producto>> obtenerProductos(
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Boolean disponibles) {
        
        log.info("GET /api/productos - categoria: {}, nombre: {}, disponibles: {}", 
                categoria, nombre, disponibles);
        
        List<Producto> productos;
        
        if (categoria != null && !categoria.isEmpty()) {
            productos = productoService.buscarPorCategoria(categoria);
        } else if (nombre != null && !nombre.isEmpty()) {
            productos = productoService.buscarPorNombre(nombre);
        } else if (Boolean.TRUE.equals(disponibles)) {
            productos = productoService.obtenerDisponibles();
        } else {
            productos = productoService.obtenerTodos();
        }
        
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Long id) {
        log.info("GET /api/productos/{}", id);
        
        Producto producto = productoService.obtenerPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto", id));
        
        return ResponseEntity.ok(producto);
    }

    @GetMapping("/categorias")
    public ResponseEntity<List<String>> obtenerCategorias() {
        log.info("GET /api/productos/categorias");
        List<String> categorias = productoService.obtenerCategorias();
        return ResponseEntity.ok(categorias);
    }

    @PostMapping
    public ResponseEntity<Producto> crearProducto(@Valid @RequestBody Producto producto) {
        log.info("POST /api/productos - Creando: {}", producto.getNombre());
        
        Producto productoCreado = productoService.crear(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoCreado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(
            @PathVariable Long id,
            @Valid @RequestBody Producto producto) {
        
        log.info("PUT /api/productos/{} - Actualizando", id);
        
        Producto productoActualizado = productoService.actualizar(id, producto);
        return ResponseEntity.ok(productoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        log.info("DELETE /api/productos/{}", id);
        
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}