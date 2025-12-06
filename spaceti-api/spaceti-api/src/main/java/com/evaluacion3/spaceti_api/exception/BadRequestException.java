
package com.evaluacion3.spaceti_api.exception;

/**
 * HTTP Status: 400 BAD REQUEST
 */
public class BadRequestException extends RuntimeException {
    
    public BadRequestException(String message) {
        super(message);
    }
    
    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}