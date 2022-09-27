package com.lucasfroque.aluraflix.services;

import com.lucasfroque.aluraflix.dto.response.VideoDto;
import com.lucasfroque.aluraflix.dto.request.VideoForm;
import com.lucasfroque.aluraflix.entities.Category;
import com.lucasfroque.aluraflix.entities.Video;
import com.lucasfroque.aluraflix.exceptions.CategoryNotFoundException;
import com.lucasfroque.aluraflix.exceptions.VideoNotFoundException;
import com.lucasfroque.aluraflix.respositories.CategoryRepository;
import com.lucasfroque.aluraflix.respositories.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class VideoService {

    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private CategoryRepository categoryRepository;


    public Video create(VideoForm videoForm){
        if(videoForm.getCategoryId() == null){
            videoForm.setCategoryId(1L);
        }
        Category category = categoryRepository.findById(videoForm.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(videoForm.getCategoryId()));
        Video video = videoForm.toVideo();
        video.setCategory(category);
        return videoRepository.save(video);
    }

    public List<VideoDto> findAll(){
        return videoRepository.findAll().stream().map(VideoDto::new).toList();
    }

    public VideoDto findById(Long id){
        Video video = videoRepository.findById(id).orElseThrow(
                () -> new VideoNotFoundException(id));
        return new VideoDto(video);
    }
    public List<VideoDto> findByTitle(String name){
        return videoRepository.findVideosByTitleContainingIgnoreCase(name)
                .stream()
                .map(VideoDto::new)
                .toList();
    }
    public VideoDto update(Long id, VideoForm videoForm){
        Video video = videoRepository.findById(id).orElseThrow(
                () -> new VideoNotFoundException(id));
        Category category = categoryRepository.findById(videoForm.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(videoForm.getCategoryId()));
        video.setTitle(videoForm.getTitle());
        video.setDescription(videoForm.getDescription());
        video.setUrl(videoForm.getUrl());
        video.setCategory(category);
        return new VideoDto(videoRepository.save(video));
    }

    public void delete(Long id){
        try {
            videoRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new VideoNotFoundException(id);
        }
    }

}
