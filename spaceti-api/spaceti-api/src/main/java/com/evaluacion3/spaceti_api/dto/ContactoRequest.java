package com.evaluacion3.spaceti_api.dto;

import jakarta.validation.constraints.*;
import lombok.*;

/**
 * DTO para recibir datos del formulario de contacto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactoRequest {
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 255, message = "El nombre debe tener entre 2 y 255 caracteres")
    private String nombre;
    
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Formato de email inválido")
    private String email;
    
    private String referencia;
    
    @NotBlank(message = "El asunto es obligatorio")
    private String asunto;
    
    @NotBlank(message = "El mensaje es obligatorio")
    @Size(min = 10, message = "El mensaje debe tener al menos 10 caracteres")
    private String mensaje;
    
    private Boolean whatsappContacto = false;
    
    private String telefono;
}