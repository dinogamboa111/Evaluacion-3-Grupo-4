package com.evaluacion3.spaceti_api.service;


import com.evaluacion3.spaceti_api.dto.ContactoRequest;
import com.evaluacion3.spaceti_api.model.FormularioContacto;
import java.util.List;
import java.util.Optional;


public interface FormularioContactoService {
    
    
    FormularioContacto crear(ContactoRequest request);
    
    
    List<FormularioContacto> obtenerTodos();
    
    
    Optional<FormularioContacto> obtenerPorId(Long id);
    
    
    List<FormularioContacto> obtenerPorEstado(String estado);
    
    
    FormularioContacto cambiarEstado(Long id, String nuevoEstado);
    
    
    void eliminar(Long id);
    
    
    List<FormularioContacto> obtenerOrdenadosPorFecha();
}