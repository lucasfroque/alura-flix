package com.lucasfroque.aluraflix.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucasfroque.aluraflix.dto.request.VideoForm;
import com.lucasfroque.aluraflix.dto.response.VideoDto;
import com.lucasfroque.aluraflix.entities.Category;
import com.lucasfroque.aluraflix.entities.Video;
import com.lucasfroque.aluraflix.exceptions.CategoryNotFoundException;
import com.lucasfroque.aluraflix.exceptions.VideoNotFoundException;
import com.lucasfroque.aluraflix.services.VideoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(VideoController.class)
@ActiveProfiles("test")
class VideoControllerTest {

    public static final String TITLE = "Video 1";
    public static final String DESCRIPTION = "Description 1";
    public static final String URL = "http://url.com";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    VideoService service;

    Video video;
    VideoDto videoDto;
    VideoForm videoForm;

    List<Video> videos;
    Page<VideoDto> videoPage;

    @BeforeEach
    void setUp() {
        video = new Video(1L, TITLE, DESCRIPTION, URL, new Category());
        videoDto = new VideoDto(video);
        videoForm = new VideoForm(TITLE, DESCRIPTION, URL, 1L);
        videos = List.of(video);
        videoPage = new PageImpl<>(List.of(videoDto));
    }

    @Test
    void when_create_then_return_video_and_status_201() throws Exception {
        when(service.create(any())).thenReturn(video);

        mockMvc.perform(post("/videos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(videoForm)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(video.getId()))
                .andExpect(jsonPath("$.title").value(video.getTitle()))
                .andExpect(jsonPath("$.description").value(video.getDescription()))
                .andExpect(jsonPath("$.url").value(video.getUrl()));
    }
    @Test
    void when_create_with_empty_field_then_return_status_400() throws Exception {
        when(service.create(any())).thenReturn(video);

        videoForm = new VideoForm("", DESCRIPTION, URL, 1L);

        mockMvc.perform(post("/videos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(videoForm)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void when_findAll_then_return_list_of_videos_and_status_200() throws Exception {
        when(service.findAll(any())).thenReturn(videoPage);


        mockMvc.perform(get("/videos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(videoDto.getId()))
                .andExpect(jsonPath("$.content[0].title").value(videoDto.getTitle()))
                .andExpect(jsonPath("$.content[0].description").value(videoDto.getDescription()))
                .andExpect(jsonPath("$.content[0].url").value(videoDto.getUrl()));
    }

    @Test
    void when_findById_then_return_video_and_status_200() throws Exception {
        when(service.findById(1L)).thenReturn(videoDto);

        mockMvc.perform(get("/videos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(videoDto.getId()))
                .andExpect(jsonPath("$.title").value(videoDto.getTitle()))
                .andExpect(jsonPath("$.description").value(videoDto.getDescription()))
                .andExpect(jsonPath("$.url").value(videoDto.getUrl()));
    }
    @Test
    void when_findById_with_invalid_id_then_return_status_404() throws Exception {
        when(service.findById(2L)).thenThrow(new VideoNotFoundException(2L));

        mockMvc.perform(get("/videos/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void when_filterByTitle_then_return_video_and_status_200() throws Exception {
        when(service.findByTitle(any(), any())).thenReturn(videoPage);

        mockMvc.perform(get("/videos/?title=" + TITLE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(videoDto.getId()))
                .andExpect(jsonPath("$.content[0].title").value(videoDto.getTitle()))
                .andExpect(jsonPath("$.content[0].description").value(videoDto.getDescription()))
                .andExpect(jsonPath("$.content[0].url").value(videoDto.getUrl()));
    }

    @Test
    void when_update_then_return_video_and_status_200() throws Exception {
        when(service.update(any(), any())).thenReturn(videoDto);

        mockMvc.perform(put("/videos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(videoForm)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(video.getId()))
                .andExpect(jsonPath("$.title").value(video.getTitle()))
                .andExpect(jsonPath("$.description").value(video.getDescription()))
                .andExpect(jsonPath("$.url").value(video.getUrl()));
    }

    @Test
    void when_update_with_empty_field_then_return_status_400() throws Exception {
        when(service.update(any(), any())).thenReturn(videoDto);

        videoForm = new VideoForm("", DESCRIPTION, URL, 1L);

        mockMvc.perform(put("/videos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(videoForm)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void when_update_with_invalid_id_then_return_status_404() throws Exception {
        when(service.update(any(), any())).thenThrow(new VideoNotFoundException(2L));

        mockMvc.perform(put("/videos/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(videoForm)))
                .andExpect(status().isNotFound());
    }

    @Test
    void when_update_with_invalid_category_then_return_status_404() throws Exception {
        when(service.update(any(), any())).thenThrow(new CategoryNotFoundException(2L));

        videoForm = new VideoForm(TITLE, DESCRIPTION, URL, 2L);

        mockMvc.perform(put("/videos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(videoForm)))
                .andExpect(status().isNotFound());
    }

    @Test
    void when_delete_then_return_status_204() throws Exception {
        doNothing().when(service).delete(1L);

        mockMvc.perform(delete("/videos/1"))
                .andExpect(status().isNoContent());
    }
    @Test
    void when_delete_with_invalid_id_then_return_status_404() throws Exception {
        doThrow(new VideoNotFoundException(2L)).when(service).delete(2L);

        mockMvc.perform(delete("/videos/2"))
                .andExpect(status().isNotFound());
    }
}