package com.lucasfroque.aluraflix.dto.request;

import com.lucasfroque.aluraflix.entities.Video;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VideoDto {
    private String title;
    private String description;
    private String url;

    public VideoDto(Video video) {
        this.title = video.getTitle();
        this.description = video.getDescription();
        this.url = video.getUrl();
    }
}
