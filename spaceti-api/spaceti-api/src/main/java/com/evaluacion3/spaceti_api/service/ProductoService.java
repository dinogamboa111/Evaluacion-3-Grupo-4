package com.evaluacion3.spaceti_api.service;


import com.evaluacion3.spaceti_api.model.Producto;
import java.util.List;
import java.util.Optional;


public interface ProductoService {
    
    
    List<Producto> obtenerTodos();
    
    
    Optional<Producto> obtenerPorId(Long id);
    
    
    Producto crear(Producto producto);
    
    
    Producto actualizar(Long id, Producto producto);
    
    
    void eliminar(Long id);
    
    
    List<Producto> buscarPorCategoria(String categoria);
    
    
    List<Producto> buscarPorNombre(String nombre);
    
    
    List<Producto> obtenerDisponibles();
    
    
    List<String> obtenerCategorias();
}