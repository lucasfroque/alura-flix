package com.lucasfroque.aluraflix.dto.response;

import com.lucasfroque.aluraflix.entities.Video;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VideoForm {
    private String title;
    private String description;
    private String url;

    public Video toVideo(){

        return new Video(title, description, url);
    }
}
