package com.evaluacion3.spaceti_api.controller;

import com.evaluacion3.spaceti_api.dto.ContactoRequest;
import com.evaluacion3.spaceti_api.exception.ResourceNotFoundException;
import com.evaluacion3.spaceti_api.model.FormularioContacto;
import com.evaluacion3.spaceti_api.service.FormularioContactoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/contacto")
@RequiredArgsConstructor
@Slf4j
public class FormularioContactoController {

    private final FormularioContactoService contactoService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> enviarMensaje(@Valid @RequestBody ContactoRequest request) {
        log.info("POST /api/contacto - Mensaje de: {}", request.getEmail());
        
        FormularioContacto contacto = contactoService.crear(request);
        
        Map<String, Object> response = Map.of(
            "success", true,
            "message", "Mensaje enviado exitosamente. Te contactaremos pronto.",
            "id", contacto.getId()
        );
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<FormularioContacto>> obtenerMensajes(
            @RequestParam(required = false) String estado) {
        
        log.info("GET /api/contacto - estado: {}", estado);
        
        List<FormularioContacto> mensajes;
        
        if (estado != null && !estado.isEmpty()) {
            mensajes = contactoService.obtenerPorEstado(estado);
        } else {
            mensajes = contactoService.obtenerOrdenadosPorFecha();
        }
        
        return ResponseEntity.ok(mensajes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormularioContacto> obtenerMensajePorId(@PathVariable Long id) {
        log.info("GET /api/contacto/{}", id);
        
        FormularioContacto mensaje = contactoService.obtenerPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mensaje de contacto", id));
        
        return ResponseEntity.ok(mensaje);
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<FormularioContacto> cambiarEstado(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        
        String nuevoEstado = body.get("estado");
        log.info("PATCH /api/contacto/{}/estado - Nuevo estado: {}", id, nuevoEstado);
        
        FormularioContacto mensajeActualizado = contactoService.cambiarEstado(id, nuevoEstado);
        return ResponseEntity.ok(mensajeActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMensaje(@PathVariable Long id) {
        log.info("DELETE /api/contacto/{}", id);
        
        contactoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}