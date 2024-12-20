package com.example.trainix.exception;

public class InvalidUserException extends RuntimeException {
    public InvalidUserException() {
        super(ExceptionConstants.INVALID_USER_EXCEPTION);
    }
}
