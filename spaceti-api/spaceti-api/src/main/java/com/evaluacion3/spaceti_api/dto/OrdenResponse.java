package com.evaluacion3.spaceti_api.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdenResponse {
    
    private Long id;
    private BigDecimal subtotal;
    private BigDecimal impuesto;
    private BigDecimal total;
    private String estado;
    private Map<String, Object> infoPago;
    private LocalDateTime createdAt;
    private List<DetalleOrdenDTO> items;
}