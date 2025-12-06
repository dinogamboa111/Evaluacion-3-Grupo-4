package com.evaluacion3.spaceti_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Entity
@Table(name = "orden")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Orden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El total es obligatorio")
    @DecimalMin(value = "0.00", message = "El total no puede ser negativo")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @NotNull(message = "El subtotal es obligatorio")
    @DecimalMin(value = "0.00", message = "El subtotal no puede ser negativo")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    @NotNull(message = "El impuesto es obligatorio")
    @DecimalMin(value = "0.00", message = "El impuesto no puede ser negativo")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal impuesto;

    @Column(length = 50, columnDefinition = "VARCHAR(50) DEFAULT 'PENDIENTE'")
    private String estado = "PENDIENTE";

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "info_pago", columnDefinition = "jsonb")
    private Map<String, Object> infoPago;

    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<DetalleOrden> detalles = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // Métodos helper para manejar la relación bidireccional
    public void addDetalle(DetalleOrden detalle) {
        detalles.add(detalle);
        detalle.setOrden(this);
    }

    public void removeDetalle(DetalleOrden detalle) {
        detalles.remove(detalle);
        detalle.setOrden(null);
    }
}