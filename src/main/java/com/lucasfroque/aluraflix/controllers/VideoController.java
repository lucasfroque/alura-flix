package com.lucasfroque.aluraflix.controllers;

import com.lucasfroque.aluraflix.dto.request.VideoDto;
import com.lucasfroque.aluraflix.dto.response.VideoForm;
import com.lucasfroque.aluraflix.entities.Video;
import com.lucasfroque.aluraflix.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/videos")
public class VideoController {

    @Autowired
    private VideoService service;

    @PostMapping
    public ResponseEntity<VideoDto> create(@RequestBody @Validated VideoForm videoForm){
        Video newVideo = service.create(videoForm);
        VideoDto videoDto = new VideoDto(newVideo);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(newVideo.getId()).toUri();

        return ResponseEntity.created(uri).body(videoDto);
    }

    @GetMapping
    public ResponseEntity<List<VideoDto>> findAll(){
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<VideoDto> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(service.findById(id));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<VideoDto> update(@PathVariable Long id, @RequestBody @Validated VideoForm videoForm){
        return ResponseEntity.ok().body(service.update(id, videoForm));
    }
}
