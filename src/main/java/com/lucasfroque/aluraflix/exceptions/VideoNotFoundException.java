package com.lucasfroque.aluraflix.exceptions;

public class VideoNotFoundException extends RuntimeException {
    public VideoNotFoundException(Long id) {
        super("Video id: " + id + " not found");
    }
}
