package com.projectbyanuj.Secure_Journal_Application.auth_services.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
