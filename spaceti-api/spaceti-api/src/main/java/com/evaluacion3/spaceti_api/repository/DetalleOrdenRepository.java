package com.evaluacion3.spaceti_api.repository;

import com.evaluacion3.spaceti_api.model.DetalleOrden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DetalleOrdenRepository extends JpaRepository<DetalleOrden, Long> {
    
    List<DetalleOrden> findByOrdenId(Long ordenId);
    
    List<DetalleOrden> findByProductoId(Long productoId);
    
    @Query("SELECT d.producto.id, SUM(d.cantidad) as total " +
           "FROM DetalleOrden d " +
           "GROUP BY d.producto.id " +
           "ORDER BY total DESC")
    List<Object[]> findTopProductos(@Param("limit") int limit);
    
    @Query("SELECT COALESCE(SUM(d.cantidad), 0) FROM DetalleOrden d WHERE d.producto.id = :productoId")
    Long countTotalVentasByProducto(@Param("productoId") Long productoId);
}