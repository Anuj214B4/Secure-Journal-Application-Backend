package com.projectbyanuj.Secure_Journal_Application.auth_services.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
