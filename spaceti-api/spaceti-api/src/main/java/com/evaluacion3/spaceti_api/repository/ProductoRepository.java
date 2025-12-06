package com.evaluacion3.spaceti_api.repository;

import com.evaluacion3.spaceti_api.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository para Producto
 * Proporciona métodos CRUD y consultas personalizadas
 */
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
    /**
     * Buscar productos por categoría
     */
    @Query("SELECT p FROM Producto p WHERE p.categoria = :categoria")
    List<Producto> findByCategoria(@Param("categoria") String categoria);
    
    /**
     * Buscar productos con stock mayor a un valor
     */
    List<Producto> findByStockGreaterThan(Integer stock);
    
    /**
     * Buscar productos por nombre (case-insensitive)
     */
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    
    /**
     * Obtener todas las categorías distintas
     */
    @Query("SELECT DISTINCT p.categoria FROM Producto p WHERE p.categoria IS NOT NULL ORDER BY p.categoria")
    List<String> findAllCategorias();
    
    /**
     * Buscar productos por categoría con stock disponible
     */
    @Query("SELECT p FROM Producto p WHERE p.categoria = :categoria AND p.stock > 0")
    List<Producto> findByCategoriaWithStock(@Param("categoria") String categoria);
}