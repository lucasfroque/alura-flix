package com.lucasfroque.aluraflix.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_category")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String color;
    @OneToMany(mappedBy = "category")
    private List<Video> video = new ArrayList<>();

    public Category(String title, String color) {
        this.title = title;
        this.color = color;
    }
}
