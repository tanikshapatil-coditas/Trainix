package com.example.trainix.exception;

public class UsernameExistsException extends RuntimeException {
    public UsernameExistsException() {
        super(ExceptionConstants.USERNAME_EXISTS);
    }
}
