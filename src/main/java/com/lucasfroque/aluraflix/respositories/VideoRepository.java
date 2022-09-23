package com.lucasfroque.aluraflix.respositories;

import com.lucasfroque.aluraflix.entities.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VideoRepository extends JpaRepository<Video, Long> {

    List<Video> findVideosByTitleContainingIgnoreCase(String name);
}
