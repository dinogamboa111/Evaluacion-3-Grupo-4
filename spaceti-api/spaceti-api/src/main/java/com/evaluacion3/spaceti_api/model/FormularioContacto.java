package com.evaluacion3.spaceti_api.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "formulario_contacto")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FormularioContacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 255, message = "El nombre debe tener entre 2 y 255 caracteres")
    @Column(nullable = false)
    private String nombre;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Formato de email inválido")
    @Size(max = 255)
    @Column(nullable = false)
    private String email;

    @Size(max = 100, message = "La referencia no puede exceder 100 caracteres")
    @Column(length = 100)
    private String referencia;

    @NotBlank(message = "El asunto es obligatorio")
    @Size(max = 255)
    @Column(nullable = false)
    private String asunto;

    @NotBlank(message = "El mensaje es obligatorio")
    @Size(min = 10, message = "El mensaje debe tener al menos 10 caracteres")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String mensaje;

    @Column(name = "whatsapp_contacto", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean whatsappContacto = false;

    @Size(max = 50, message = "El teléfono no puede exceder 50 caracteres")
    @Column(length = 50)
    private String telefono;

    @Column(length = 50, columnDefinition = "VARCHAR(50) DEFAULT 'PENDIENTE'")
    private String estado = "PENDIENTE";

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}