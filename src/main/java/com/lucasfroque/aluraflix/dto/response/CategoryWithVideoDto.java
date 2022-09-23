package com.lucasfroque.aluraflix.dto.response;

import com.lucasfroque.aluraflix.entities.Category;
import lombok.Getter;

import java.util.List;

@Getter
public class CategoryWithVideoDto {

    private Long id;
    private String title;
    private String color;
    private List<VideoDto> videos;

    public CategoryWithVideoDto(Category category) {
        this.id = category.getId();
        this.title = category.getTitle();
        this.color = category.getColor();
        this.videos = category.getVideo()
                .stream()
                .map(VideoDto::new)
                .toList();
    }
}
