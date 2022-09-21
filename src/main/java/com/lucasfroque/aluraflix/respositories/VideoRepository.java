package com.lucasfroque.aluraflix.respositories;

import com.lucasfroque.aluraflix.entities.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Long> {
}
