package com.evaluacion3.spaceti_api.repository;

import com.evaluacion3.spaceti_api.model.FormularioContacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository para FormularioContacto
 * Gestiona el acceso a datos de mensajes de contacto
 */
@Repository
public interface FormularioContactoRepository extends JpaRepository<FormularioContacto, Long> {
    
    List<FormularioContacto> findByEstado(String estado);
    
    List<FormularioContacto> findByEmail(String email);
    
    List<FormularioContacto> findByWhatsappContactoTrue();
    
    List<FormularioContacto> findAllByOrderByCreatedAtDesc();
    
    Long countByEstado(String estado);
    
    List<FormularioContacto> findByReferencia(String referencia);
}