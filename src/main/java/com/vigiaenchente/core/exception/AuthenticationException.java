package com.vigiaenchente.core.exception;

/**
 * Exception for authentication failures
 */
public class AuthenticationException extends RuntimeException {

    public AuthenticationException(String message) {
        super(message);
    }
}
