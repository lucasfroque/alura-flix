package com.lucasfroque.aluraflix.dto.response;

import com.lucasfroque.aluraflix.entities.Video;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VideoForm {

    @NotBlank(message = "title cannot be null or empty")
    private String title;
    @NotBlank(message = "description cannot be null or empty")
    @Size(min = 10, message = "description must have at least 10 characters")
    private String description;
    @NotBlank(message = "url cannot be null or empty")
    @Size(min = 10, message = "url must have at least 10 characters")
    private String url;

    public Video toVideo(){

        return new Video(title, description, url);
    }
}
