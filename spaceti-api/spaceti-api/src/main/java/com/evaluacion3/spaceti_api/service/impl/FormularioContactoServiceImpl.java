package com.evaluacion3.spaceti_api.service.impl;

import com.evaluacion3.spaceti_api.dto.ContactoRequest;
import com.evaluacion3.spaceti_api.exception.BadRequestException;
import com.evaluacion3.spaceti_api.exception.ResourceNotFoundException;
import com.evaluacion3.spaceti_api.model.FormularioContacto;
import com.evaluacion3.spaceti_api.repository.FormularioContactoRepository;
import com.evaluacion3.spaceti_api.service.FormularioContactoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio de FormularioContacto
 * Contiene la lógica de negocio para mensajes de contacto
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class FormularioContactoServiceImpl implements FormularioContactoService {

    private final FormularioContactoRepository contactoRepository;

    @Override
    public FormularioContacto crear(ContactoRequest request) {
        log.info("Creando nuevo mensaje de contacto de: {}", request.getEmail());
        
        // Validar teléfono si whatsapp está activo
        if (Boolean.TRUE.equals(request.getWhatsappContacto()) && 
            (request.getTelefono() == null || request.getTelefono().isBlank())) {
            throw new BadRequestException("El teléfono es obligatorio cuando se solicita contacto por WhatsApp");
        }
        
        // Convertir DTO a entidad
        FormularioContacto contacto = FormularioContacto.builder()
                .nombre(request.getNombre())
                .email(request.getEmail())
                .referencia(request.getReferencia())
                .asunto(request.getAsunto())
                .mensaje(request.getMensaje())
                .whatsappContacto(request.getWhatsappContacto())
                .telefono(request.getTelefono())
                .estado("PENDIENTE")
                .build();
        
        FormularioContacto contactoGuardado = contactoRepository.save(contacto);
        log.info("Mensaje de contacto creado exitosamente con ID: {}", contactoGuardado.getId());
        return contactoGuardado;
    }

    @Override
    @Transactional(readOnly = true)
    public List<FormularioContacto> obtenerTodos() {
        log.info("Obteniendo todos los mensajes de contacto");
        return contactoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FormularioContacto> obtenerPorId(Long id) {
        log.info("Buscando mensaje de contacto con ID: {}", id);
        return contactoRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FormularioContacto> obtenerPorEstado(String estado) {
        log.info("Buscando mensajes por estado: {}", estado);
        return contactoRepository.findByEstado(estado);
    }

    @Override
    public FormularioContacto cambiarEstado(Long id, String nuevoEstado) {
        log.info("Cambiando estado del mensaje {} a: {}", id, nuevoEstado);
        
        FormularioContacto contacto = contactoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mensaje de contacto", id));
        
        // Validar estados permitidos
        List<String> estadosValidos = List.of("PENDIENTE", "EN_PROCESO", "ATENDIDO", "CERRADO");
        if (!estadosValidos.contains(nuevoEstado)) {
            throw new BadRequestException("Estado no válido. Estados permitidos: " + estadosValidos);
        }
        
        contacto.setEstado(nuevoEstado);
        FormularioContacto contactoActualizado = contactoRepository.save(contacto);
        log.info("Estado actualizado exitosamente");
        return contactoActualizado;
    }

    @Override
    public void eliminar(Long id) {
        log.info("Eliminando mensaje de contacto con ID: {}", id);
        
        if (!contactoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Mensaje de contacto", id);
        }
        
        contactoRepository.deleteById(id);
        log.info("Mensaje de contacto eliminado exitosamente");
    }

    @Override
    @Transactional(readOnly = true)
    public List<FormularioContacto> obtenerOrdenadosPorFecha() {
        log.info("Obteniendo mensajes ordenados por fecha");
        return contactoRepository.findAllByOrderByCreatedAtDesc();
    }
}