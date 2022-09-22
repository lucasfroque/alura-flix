package com.lucasfroque.aluraflix.dto.response;

import com.lucasfroque.aluraflix.entities.Category;
import lombok.Getter;

@Getter
public class CategoryDto {
    private String title;
    private String color;

    public CategoryDto(Category category) {
        this.title = category.getTitle();
        this.color = category.getColor();
    }
}
