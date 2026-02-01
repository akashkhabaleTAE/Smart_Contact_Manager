package com.smart.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
        super("Resource not not found!");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
