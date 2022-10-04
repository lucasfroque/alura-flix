package com.lucasfroque.aluraflix.controllers;

import com.lucasfroque.aluraflix.dto.response.VideoDto;
import com.lucasfroque.aluraflix.dto.request.VideoForm;
import com.lucasfroque.aluraflix.entities.Video;
import com.lucasfroque.aluraflix.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/videos")
public class VideoController {

    @Autowired
    private VideoService service;

    @PostMapping
    public ResponseEntity<VideoDto> create(@RequestBody @Valid VideoForm videoForm){
        Video newVideo = service.create(videoForm);
        VideoDto videoDto = new VideoDto(newVideo);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(newVideo.getId()).toUri();

        return ResponseEntity.created(uri).body(videoDto);
    }

    @GetMapping
    public ResponseEntity<Page<VideoDto>> findAll(@RequestParam (required = false) String title,
                                                  @PageableDefault(size = 10, direction = Direction.DESC, sort = "id") Pageable pageable){

        if(title != null){
            return ResponseEntity.ok().body(service.findByTitle(title, pageable));
        }

        return ResponseEntity.ok().body(service.findAll(pageable));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<VideoDto> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(service.findById(id));
    }

    @GetMapping(value = "/free")
    public ResponseEntity<Page<VideoDto>> findFreeVideos(@PageableDefault(size = 10, direction = Direction.DESC, sort = "id") Pageable pageable){
        return ResponseEntity.ok().body(service.findFreeVideos(pageable));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<VideoDto> update(@PathVariable Long id, @RequestBody @Valid VideoForm videoForm){
        return ResponseEntity.ok().body(service.update(id, videoForm));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        service.delete(id);
       return ResponseEntity.noContent().build();
    }
}
