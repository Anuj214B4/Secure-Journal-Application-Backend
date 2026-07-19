package com.projectbyanuj.Secure_Journal_Application.auth_services.exception;

public class TokenRefreshException extends RuntimeException {
    public TokenRefreshException(String message) {
        super(message);
    }
}
