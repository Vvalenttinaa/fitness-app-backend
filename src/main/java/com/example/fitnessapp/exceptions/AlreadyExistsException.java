package com.example.fitnessapp.exceptions;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException() {
        super("Already exists");
    }

    public AlreadyExistsException(String message) {
        super(message);
    }
}
