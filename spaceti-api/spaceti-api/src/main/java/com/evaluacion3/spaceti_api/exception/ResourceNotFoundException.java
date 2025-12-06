package com.evaluacion3.spaceti_api.exception;

/**
 * HTTP Status: 404 NOT FOUND
 */
public class ResourceNotFoundException extends RuntimeException {
    
    public ResourceNotFoundException(String message) {
        super(message);
    }
    
    public ResourceNotFoundException(String resource, Long id) {
        super(String.format("%s con ID %d no encontrado", resource, id));
    }
    
    public ResourceNotFoundException(String resource, String field, Object value) {
        super(String.format("%s con %s '%s' no encontrado", resource, field, value));
    }
}