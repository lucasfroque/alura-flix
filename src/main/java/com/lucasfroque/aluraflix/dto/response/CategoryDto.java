package com.lucasfroque.aluraflix.dto.response;

import com.lucasfroque.aluraflix.entities.Category;
import lombok.Getter;

@Getter
public class CategoryDto {
    private Long id;
    private String title;
    private String color;

    public CategoryDto(Category category) {
        this.id = category.getId();
        this.title = category.getTitle();
        this.color = category.getColor();
    }
}
