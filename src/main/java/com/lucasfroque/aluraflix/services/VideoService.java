package com.lucasfroque.aluraflix.services;

import com.lucasfroque.aluraflix.dto.response.VideoForm;
import com.lucasfroque.aluraflix.entities.Video;
import com.lucasfroque.aluraflix.respositories.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class VideoService {

    @Autowired
    private VideoRepository repository;

    public Video create(VideoForm videoForm){
        return repository.save(videoForm.toVideo());
    }

}
