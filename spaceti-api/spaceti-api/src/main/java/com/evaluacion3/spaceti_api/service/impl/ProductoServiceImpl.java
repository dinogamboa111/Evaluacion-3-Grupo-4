package com.evaluacion3.spaceti_api.service.impl;



import com.evaluacion3.spaceti_api.exception.BadRequestException;
import com.evaluacion3.spaceti_api.exception.ResourceNotFoundException;
import com.evaluacion3.spaceti_api.model.Producto;
import com.evaluacion3.spaceti_api.repository.ProductoRepository;
import com.evaluacion3.spaceti_api.service.ProductoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio de Producto
 * Contiene la lógica de negocio para gestión de productos
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Producto> obtenerTodos() {
        log.info("Obteniendo todos los productos");
        return productoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Producto> obtenerPorId(Long id) {
        log.info("Buscando producto con ID: {}", id);
        return productoRepository.findById(id);
    }

    @Override
    public Producto crear(Producto producto) {
        log.info("Creando nuevo producto: {}", producto.getNombre());
        
        // Validaciones de negocio
        if (producto.getPrecio().doubleValue() <= 0) {
            throw new BadRequestException("El precio debe ser mayor a 0");
        }
        
        if (producto.getStock() < 0) {
            throw new BadRequestException("El stock no puede ser negativo");
        }
        
        Producto productoGuardado = productoRepository.save(producto);
        log.info("Producto creado exitosamente con ID: {}", productoGuardado.getId());
        return productoGuardado;
    }

    @Override
    public Producto actualizar(Long id, Producto producto) {
        log.info("Actualizando producto con ID: {}", id);
        
        Producto productoExistente = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto", id));
        
        // Actualizar campos
        productoExistente.setNombre(producto.getNombre());
        productoExistente.setDescripcion(producto.getDescripcion());
        productoExistente.setPrecio(producto.getPrecio());
        productoExistente.setImagenUrl(producto.getImagenUrl());
        productoExistente.setStock(producto.getStock());
        productoExistente.setCategoria(producto.getCategoria());
        
        Producto productoActualizado = productoRepository.save(productoExistente);
        log.info("Producto actualizado exitosamente: {}", productoActualizado.getId());
        return productoActualizado;
    }

    @Override
    public void eliminar(Long id) {
        log.info("Eliminando producto con ID: {}", id);
        
        if (!productoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Producto", id);
        }
        
        productoRepository.deleteById(id);
        log.info("Producto eliminado exitosamente: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> buscarPorCategoria(String categoria) {
        log.info("Buscando productos por categoría: {}", categoria);
        return productoRepository.findByCategoria(categoria);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> buscarPorNombre(String nombre) {
        log.info("Buscando productos por nombre: {}", nombre);
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> obtenerDisponibles() {
        log.info("Obteniendo productos con stock disponible");
        return productoRepository.findByStockGreaterThan(0);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> obtenerCategorias() {
        log.info("Obteniendo todas las categorías");
        return productoRepository.findAllCategorias();
    }
}