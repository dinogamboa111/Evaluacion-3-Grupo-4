package com.evaluacion3.spaceti_api.service.impl;

import com.evaluacion3.spaceti_api.dto.DetalleOrdenDTO;
import com.evaluacion3.spaceti_api.dto.OrdenRequest;
import com.evaluacion3.spaceti_api.dto.OrdenResponse;
import com.evaluacion3.spaceti_api.exception.BadRequestException;
import com.evaluacion3.spaceti_api.exception.ResourceNotFoundException;
import com.evaluacion3.spaceti_api.model.DetalleOrden;
import com.evaluacion3.spaceti_api.model.Orden;
import com.evaluacion3.spaceti_api.model.Producto;
import com.evaluacion3.spaceti_api.repository.OrdenRepository;
import com.evaluacion3.spaceti_api.repository.ProductoRepository;
import com.evaluacion3.spaceti_api.service.OrdenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrdenServiceImpl implements OrdenService {

    private final OrdenRepository ordenRepository;
    private final ProductoRepository productoRepository;
    
    // Tasa de impuesto (19% IVA en Chile)
    private static final BigDecimal TASA_IMPUESTO = new BigDecimal("0.19");

    @Override
    public OrdenResponse crearOrden(OrdenRequest request) {
        log.info("Creando nueva orden con {} items", request.getItems().size());
        
        // Validar que hay items
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new BadRequestException("La orden debe contener al menos un producto");
        }
        
        // Crear la orden
        Orden orden = new Orden();
        orden.setEstado("PENDIENTE");
        orden.setInfoPago(request.getInfoPago());
        orden.setDetalles(new ArrayList<>());
        
        BigDecimal subtotal = BigDecimal.ZERO;
        
        // Procesar cada item del carrito
        for (DetalleOrdenDTO itemDTO : request.getItems()) {
            // Buscar el producto
            Producto producto = productoRepository.findById(itemDTO.getProductoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Producto", itemDTO.getProductoId()));
            
            // Validar stock
            if (producto.getStock() < itemDTO.getCantidad()) {
                throw new BadRequestException(
                    String.format("Stock insuficiente para el producto '%s'. Disponible: %d, Solicitado: %d",
                        producto.getNombre(), producto.getStock(), itemDTO.getCantidad())
                );
            }
            
            // Crear detalle de orden
            DetalleOrden detalle = DetalleOrden.builder()
                    .producto(producto)
                    .cantidad(itemDTO.getCantidad())
                    .precioUnitario(producto.getPrecio())
                    .build();
            
            // El subtotal se calcula automáticamente en @PrePersist del DetalleOrden
            BigDecimal subtotalItem = producto.getPrecio().multiply(BigDecimal.valueOf(itemDTO.getCantidad()));
            subtotal = subtotal.add(subtotalItem);
            
            // Agregar detalle a la orden
            orden.addDetalle(detalle);
            
            // Reducir stock del producto
            producto.setStock(producto.getStock() - itemDTO.getCantidad());
            productoRepository.save(producto);
            
            log.info("Item agregado: {} x{} - ${}", producto.getNombre(), itemDTO.getCantidad(), subtotalItem);
        }
        
        // Calcular impuesto y total
        BigDecimal impuesto = subtotal.multiply(TASA_IMPUESTO);
        BigDecimal total = subtotal.add(impuesto);
        
        orden.setSubtotal(subtotal);
        orden.setImpuesto(impuesto);
        orden.setTotal(total);
        
        // Guardar orden
        Orden ordenGuardada = ordenRepository.save(orden);
        log.info("Orden creada exitosamente con ID: {} - Total: ${}", ordenGuardada.getId(), total);
        
        // Convertir a response
        return convertirAOrdenResponse(ordenGuardada);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Orden> obtenerTodas() {
        log.info("Obteniendo todas las órdenes");
        return ordenRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrdenResponse> obtenerPorId(Long id) {
        log.info("Buscando orden con ID: {}", id);
        return ordenRepository.findByIdWithDetalles(id)
                .map(this::convertirAOrdenResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Orden> obtenerPorEstado(String estado) {
        log.info("Buscando órdenes por estado: {}", estado);
        return ordenRepository.findByEstado(estado);
    }

    @Override
    public Orden cambiarEstado(Long id, String nuevoEstado) {
        log.info("Cambiando estado de la orden {} a: {}", id, nuevoEstado);
        
        Orden orden = ordenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Orden", id));
        
        
        List<String> estadosValidos = List.of("PENDIENTE", "PROCESANDO", "COMPLETADA", "CANCELADA");
        if (!estadosValidos.contains(nuevoEstado)) {
            throw new BadRequestException("Estado no válido. Estados permitidos: " + estadosValidos);
        }
        
        orden.setEstado(nuevoEstado);
        Orden ordenActualizada = ordenRepository.save(orden);
        log.info("Estado de orden actualizado exitosamente");
        return ordenActualizada;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Orden> obtenerOrdenadasPorFecha() {
        log.info("Obteniendo órdenes ordenadas por fecha");
        return ordenRepository.findAllByOrderByCreatedAtDesc();
    }

    
    private OrdenResponse convertirAOrdenResponse(Orden orden) {
        List<DetalleOrdenDTO> itemsDTO = orden.getDetalles().stream()
                .map(detalle -> DetalleOrdenDTO.builder()
                        .productoId(detalle.getProducto().getId())
                        .nombreProducto(detalle.getProducto().getNombre())
                        .cantidad(detalle.getCantidad())
                        .precioUnitario(detalle.getPrecioUnitario())
                        .subtotal(detalle.getSubtotal())
                        .build())
                .collect(Collectors.toList());
        
        return OrdenResponse.builder()
                .id(orden.getId())
                .subtotal(orden.getSubtotal())
                .impuesto(orden.getImpuesto())
                .total(orden.getTotal())
                .estado(orden.getEstado())
                .infoPago(orden.getInfoPago())
                .createdAt(orden.getCreatedAt())
                .items(itemsDTO)
                .build();
    }
}