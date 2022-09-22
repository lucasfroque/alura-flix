package com.lucasfroque.aluraflix.dto.request;

import com.lucasfroque.aluraflix.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
public class CategoryForm {

    @NotBlank(message = "title cannot be null or empty")
    private String title;
    @NotBlank(message = "color cannot be null or empty")
    private String color;

    public Category toCategory(){
        return new Category(title, color);
    }
}
