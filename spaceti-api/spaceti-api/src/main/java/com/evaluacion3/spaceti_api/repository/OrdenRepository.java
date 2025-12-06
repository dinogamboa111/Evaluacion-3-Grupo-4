package com.evaluacion3.spaceti_api.repository;

import com.evaluacion3.spaceti_api.model.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository para Orden
 * Gestiona el acceso a datos de órdenes de compra
 */
@Repository
public interface OrdenRepository extends JpaRepository<Orden, Long> {
    
    List<Orden> findByEstado(String estado);
    
    List<Orden> findAllByOrderByCreatedAtDesc();
    
    @Query("SELECT o FROM Orden o LEFT JOIN FETCH o.detalles WHERE o.id = :id")
    Optional<Orden> findByIdWithDetalles(@Param("id") Long id);
    
    @Query("SELECT o FROM Orden o WHERE o.createdAt BETWEEN :inicio AND :fin ORDER BY o.createdAt DESC")
    List<Orden> findByFechaRange(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);
    
    Long countByEstado(String estado);
    
    @Query("SELECT o FROM Orden o LEFT JOIN FETCH o.detalles WHERE o.estado = 'COMPLETADA' ORDER BY o.createdAt DESC")
    List<Orden> findCompletadasWithDetalles();
}