package com.lucasfroque.aluraflix.services;

import com.lucasfroque.aluraflix.dto.request.VideoDto;
import com.lucasfroque.aluraflix.dto.response.VideoForm;
import com.lucasfroque.aluraflix.entities.Video;
import com.lucasfroque.aluraflix.exceptions.ResourceNotFoundException;
import com.lucasfroque.aluraflix.respositories.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class VideoService {

    @Autowired
    private VideoRepository repository;

    public Video create(VideoForm videoForm){
        return repository.save(videoForm.toVideo());
    }

    public List<VideoDto> findAll(){
        return repository.findAll().stream().map(VideoDto::new).toList();
    }

    public VideoDto findById(Long id){
        Video video = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Video id: " + id + " not found"));
        return new VideoDto(video);
    }
}
