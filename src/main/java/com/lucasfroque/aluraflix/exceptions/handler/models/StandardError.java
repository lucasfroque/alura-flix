package com.lucasfroque.aluraflix.exceptions.handler.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class StandardError {

    private String message;
    private String error;
    private Integer status;
}
