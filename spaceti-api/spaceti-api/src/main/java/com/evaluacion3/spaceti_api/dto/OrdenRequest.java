package com.evaluacion3.spaceti_api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;
import java.util.Map;

/**
 * DTO para recibir datos al crear una orden
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdenRequest {
    
    @NotEmpty(message = "La orden debe contener al menos un producto")
    @Valid
    private List<DetalleOrdenDTO> items;
    
    private Map<String, Object> infoPago;
}