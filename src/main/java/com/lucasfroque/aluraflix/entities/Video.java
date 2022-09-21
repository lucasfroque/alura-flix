package com.lucasfroque.aluraflix.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "tb_videos")
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String url;

    public Video(String title, String description, String url) {
        this.title = title;
        this.description = description;
        this.url = url;
    }
}
