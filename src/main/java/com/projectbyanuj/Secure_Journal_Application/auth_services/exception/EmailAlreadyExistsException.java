package com.projectbyanuj.Secure_Journal_Application.auth_services.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String message){
        super(message);
    }
}
