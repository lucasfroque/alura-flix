package com.lucasfroque.aluraflix.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("User id: " + id + " not found");
    }
    public UserNotFoundException(String name) {
        super("User name: " + name + " not found");
    }
}
