package com.evaluacion3.spaceti_api.service;


import com.evaluacion3.spaceti_api.dto.OrdenRequest;
import com.evaluacion3.spaceti_api.dto.OrdenResponse;
import com.evaluacion3.spaceti_api.model.Orden;
import java.util.List;
import java.util.Optional;


public interface OrdenService {
    
    
    OrdenResponse crearOrden(OrdenRequest request);
    
    
    List<Orden> obtenerTodas();
    
    
    Optional<OrdenResponse> obtenerPorId(Long id);
    
    
    List<Orden> obtenerPorEstado(String estado);
    
    
    Orden cambiarEstado(Long id, String nuevoEstado);
    
    
    List<Orden> obtenerOrdenadasPorFecha();
}