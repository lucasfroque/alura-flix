package com.lucasfroque.aluraflix.exceptions;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(Long id) {
        super("Video id: " + id + " not found");
    }
}
