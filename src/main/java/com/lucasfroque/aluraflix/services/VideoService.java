package com.lucasfroque.aluraflix.services;

import com.lucasfroque.aluraflix.dto.response.VideoDto;
import com.lucasfroque.aluraflix.dto.request.VideoForm;
import com.lucasfroque.aluraflix.entities.Video;
import com.lucasfroque.aluraflix.exceptions.VideoNotFoundException;
import com.lucasfroque.aluraflix.respositories.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
                () -> new VideoNotFoundException(id));
        return new VideoDto(video);
    }

    public VideoDto update(Long id, VideoForm videoForm){
        Video video = repository.findById(id).orElseThrow(
                () -> new VideoNotFoundException(id));
        video.setTitle(videoForm.getTitle());
        video.setDescription(videoForm.getDescription());
        video.setUrl(videoForm.getUrl());
        return new VideoDto(repository.save(video));
    }

    public void delete(Long id){
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new VideoNotFoundException(id);
        }
    }

}
