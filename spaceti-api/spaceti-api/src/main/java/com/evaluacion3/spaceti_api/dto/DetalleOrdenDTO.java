package com.evaluacion3.spaceti_api.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleOrdenDTO {
    
    @NotNull(message = "El ID del producto es obligatorio")
    private Long productoId;
    
    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer cantidad;
    
    // Campos para response
    private String nombreProducto;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
}