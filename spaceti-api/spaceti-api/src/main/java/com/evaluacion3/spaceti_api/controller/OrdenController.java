package com.evaluacion3.spaceti_api.controller;

import com.evaluacion3.spaceti_api.dto.OrdenRequest;
import com.evaluacion3.spaceti_api.dto.OrdenResponse;
import com.evaluacion3.spaceti_api.exception.ResourceNotFoundException;
import com.evaluacion3.spaceti_api.model.Orden;
import com.evaluacion3.spaceti_api.service.OrdenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/ordenes")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class OrdenController {

    private final OrdenService ordenService;

    
    @PostMapping
    public ResponseEntity<OrdenResponse> crearOrden(@Valid @RequestBody OrdenRequest request) {
        log.info("POST /api/ordenes - Creando orden con {} items", request.getItems().size());
        
        OrdenResponse ordenCreada = ordenService.crearOrden(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ordenCreada);
    }

    
    @GetMapping
    public ResponseEntity<List<Orden>> obtenerOrdenes(
            @RequestParam(required = false) String estado) {
        
        log.info("GET /api/ordenes - estado: {}", estado);
        
        List<Orden> ordenes;
        
        if (estado != null && !estado.isEmpty()) {
            ordenes = ordenService.obtenerPorEstado(estado);
        } else {
            ordenes = ordenService.obtenerOrdenadasPorFecha();
        }
        
        return ResponseEntity.ok(ordenes);
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<OrdenResponse> obtenerOrdenPorId(@PathVariable Long id) {
        log.info("GET /api/ordenes/{}", id);
        
        OrdenResponse orden = ordenService.obtenerPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Orden", id));
        
        return ResponseEntity.ok(orden);
    }

    
    @PatchMapping("/{id}/estado")
    public ResponseEntity<Orden> cambiarEstado(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        
        String nuevoEstado = body.get("estado");
        log.info("PATCH /api/ordenes/{}/estado - Nuevo estado: {}", id, nuevoEstado);
        
        Orden ordenActualizada = ordenService.cambiarEstado(id, nuevoEstado);
        return ResponseEntity.ok(ordenActualizada);
    }

    
    @GetMapping("/estadisticas")
    public ResponseEntity<Map<String, Object>> obtenerEstadisticas() {
        log.info("GET /api/ordenes/estadisticas");
        
        List<Orden> todasLasOrdenes = ordenService.obtenerTodas();
        
        long totalOrdenes = todasLasOrdenes.size();
        long ordenesPendientes = todasLasOrdenes.stream()
                .filter(o -> "PENDIENTE".equals(o.getEstado()))
                .count();
        long ordenesCompletadas = todasLasOrdenes.stream()
                .filter(o -> "COMPLETADA".equals(o.getEstado()))
                .count();
        
        Map<String, Object> estadisticas = Map.of(
            "totalOrdenes", totalOrdenes,
            "ordenesPendientes", ordenesPendientes,
            "ordenesCompletadas", ordenesCompletadas,
            "ordenesCanceladas", todasLasOrdenes.stream()
                    .filter(o -> "CANCELADA".equals(o.getEstado()))
                    .count()
        );
        
        return ResponseEntity.ok(estadisticas);
    }
}