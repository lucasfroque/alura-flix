package com.lucasfroque.aluraflix.services;

import com.lucasfroque.aluraflix.dto.request.VideoForm;
import com.lucasfroque.aluraflix.dto.response.VideoDto;
import com.lucasfroque.aluraflix.entities.Category;
import com.lucasfroque.aluraflix.entities.Video;
import com.lucasfroque.aluraflix.exceptions.CategoryNotFoundException;
import com.lucasfroque.aluraflix.exceptions.VideoNotFoundException;
import com.lucasfroque.aluraflix.respositories.CategoryRepository;
import com.lucasfroque.aluraflix.respositories.VideoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VideoServiceTest {

    public static final long CATEGORY_ID_1 = 1L;
    public static final String CATEGORY_TITLE_1 = "Category 1";
    public static final String CATEGORY_COLOR_1 = "#000000";
    public static final long CATEGORY_ID_2 = 2L;
    public static final String CATEGORY_TITLE_2 = "Category 2";
    public static final String CATEGORY_COLOR_2 = "#111111";
    public static final long VIDEO_ID = 1L;
    public static final String VIDEO_TITLE = "Video 1";
    public static final String VIDEO_DESCRIPTION = "Description 1";
    public static final String VIDEO_URL = "https://www.url.com";

    @InjectMocks
    VideoService service;
    @Mock
    VideoRepository videoRepository;
    @Mock
    CategoryRepository categoryRepository;

    Video video1;
    Video video2;
    VideoForm videoForm;
    VideoForm videoFormWithoutCategory;
    Category category;
    Category category2;
    Page<Video> videoPage;

    @BeforeEach
    void setUp() {
        category = new Category(CATEGORY_TITLE_1, CATEGORY_COLOR_1);
        category.setId(CATEGORY_ID_1);
        category2 = new Category(CATEGORY_TITLE_2, CATEGORY_COLOR_2);
        category2.setId(CATEGORY_ID_2);

        video1 = new Video(VIDEO_ID, VIDEO_TITLE, VIDEO_DESCRIPTION, VIDEO_URL, category);
        video2 = new Video(VIDEO_ID, VIDEO_TITLE, VIDEO_DESCRIPTION, VIDEO_URL, category2);

        videoForm = new VideoForm(VIDEO_TITLE, VIDEO_DESCRIPTION, VIDEO_URL, CATEGORY_ID_2);
        videoFormWithoutCategory = new VideoForm(VIDEO_TITLE, VIDEO_DESCRIPTION, VIDEO_URL, null);
        videoPage = new PageImpl<>(List.of(video1, video2));
    }

    @Test
    void should_create_video_successfully() {
        when(videoRepository.save(any(Video.class)))
                .thenReturn(video2);
        when(categoryRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(category2));

        Video response = service.create(videoForm);

        assertEquals(video2, response);
        assertEquals(VIDEO_ID, response.getId());
        assertEquals(VIDEO_TITLE, response.getTitle());
        assertEquals(VIDEO_DESCRIPTION, response.getDescription());
        assertEquals(VIDEO_URL, response.getUrl());
        assertEquals(category2, response.getCategory());
    }
    @Test
    void should_create_video_with_categoryId_1() {
        when(videoRepository.save(any(Video.class)))
                .thenReturn(video1);
        when(categoryRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(category));

        Video response = service.create(videoFormWithoutCategory);

        assertEquals(video1, response);
        assertEquals(response.getId(), VIDEO_ID);
        assertEquals(response.getTitle(), VIDEO_TITLE);
        assertEquals(response.getDescription(), VIDEO_DESCRIPTION);
        assertEquals(response.getUrl(), VIDEO_URL);
        assertNotNull(response.getCategory());
        assertEquals(response.getCategory().getId(), CATEGORY_ID_1);
    }

    @Test
    void should_throw_exception_when_categoryId_is_not_found_when_create_video() {
        when(categoryRepository.findById(any(Long.class)))
                .thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> service.create(videoForm));
    }

    @Test
    void should_return_list_of_videos() {
        when(videoRepository.findAll(any(Pageable.class)))
                .thenReturn(videoPage);

        List<VideoDto> response = service.findAll(Pageable.ofSize(10)).toList();

        assertEquals(2, response.size());
        assertEquals(VIDEO_ID, response.get(0).getId());
        assertEquals(VIDEO_TITLE, response.get(0).getTitle());
        assertEquals(VIDEO_DESCRIPTION, response.get(0).getDescription());
        assertEquals(VIDEO_URL, response.get(0).getUrl());
    }

    @Test
    void should_return_video_by_id() {
        when(videoRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(video1));

        VideoDto response = service.findById(VIDEO_ID);

        assertEquals(response.getClass(), VideoDto.class);
        assertEquals(response.getId(), VIDEO_ID);
        assertEquals(response.getTitle(), VIDEO_TITLE);
        assertEquals(response.getDescription(), VIDEO_DESCRIPTION);
        assertEquals(response.getUrl(), VIDEO_URL);
    }

    @Test
    void should_throw_exception_when_video_id_is_not_found_when_find_by_id() {
        when(videoRepository.findById(any(Long.class)))
                .thenReturn(Optional.empty());

        assertThrows(VideoNotFoundException.class, () -> service.findById(VIDEO_ID));
    }

    @Test
    void should_return_list_of_video_filtered_by_title() {

        when(videoRepository.findVideosByTitleContainingIgnoreCase(any(), any()))
                .thenReturn(videoPage);

        List<VideoDto> response = service.findByTitle(VIDEO_TITLE, Pageable.ofSize(10)).toList();

        assertEquals(2, response.size());
        assertEquals(response.get(0).getClass(), VideoDto.class);
        assertEquals(VIDEO_ID, response.get(0).getId());
        assertEquals(VIDEO_TITLE, response.get(0).getTitle());
        assertEquals(VIDEO_DESCRIPTION, response.get(0).getDescription());
        assertEquals(VIDEO_URL, response.get(0).getUrl());
    }

    @Test
    void should_update_video_successfully() {
        when(videoRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(video2));
        when(categoryRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(category2));
        when(videoRepository.save(any(Video.class)))
                .thenReturn(video2);

        VideoDto response = service.update(VIDEO_ID, videoForm);

        assertEquals(response.getClass(), VideoDto.class);
        assertEquals(VIDEO_ID, response.getId());
        assertEquals(VIDEO_TITLE, response.getTitle());
        assertEquals(VIDEO_DESCRIPTION, response.getDescription());
        assertEquals(VIDEO_URL, response.getUrl());
    }

    @Test
    void should_throw_exception_when_video_id_is_not_found_when_update() {
        when(videoRepository.findById(any(Long.class)))
                .thenReturn(Optional.empty());

        assertThrows(VideoNotFoundException.class, () -> service.update(VIDEO_ID, videoForm));
    }

    @Test
    void should_throw_exception_when_category_id_is_not_found_when_update() {
        when(videoRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(video2));
        when(categoryRepository.findById(any(Long.class)))
                .thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> service.update(VIDEO_ID, videoForm));
    }

    @Test
    void delete_video_successfully() {
        service.delete(VIDEO_ID);

        verify(videoRepository, times(1)).deleteById(VIDEO_ID);
    }

    @Test
    void should_throw_exception_when_video_id_is_not_found_when_delete() {
        doThrow(VideoNotFoundException.class).when(videoRepository).deleteById(any(Long.class));

        assertThrows(VideoNotFoundException.class, () -> service.delete(VIDEO_ID));

    }
}