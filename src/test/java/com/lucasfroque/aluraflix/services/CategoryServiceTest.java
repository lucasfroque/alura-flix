package com.lucasfroque.aluraflix.services;

import com.lucasfroque.aluraflix.dto.request.CategoryForm;
import com.lucasfroque.aluraflix.dto.response.CategoryDto;
import com.lucasfroque.aluraflix.dto.response.CategoryWithVideoDto;
import com.lucasfroque.aluraflix.entities.Category;
import com.lucasfroque.aluraflix.entities.Video;
import com.lucasfroque.aluraflix.exceptions.CategoryNotFoundException;
import com.lucasfroque.aluraflix.respositories.CategoryRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    public static final String CATEGORY = "Category 1";
    public static final String COLOR = "#000000";
    public static final long CATEGORY_ID = 1L;
    public static final String VIDEO_TITLE = "Video 1";
    public static final String VIDEO_DESCRIPTION = "Description 1";
    public static final String VIDEO_URL = "https://www.url.com";
    @InjectMocks
    CategoryService service;
    @Mock
    CategoryRepository repository;

    Category category;

    CategoryForm categoryForm;

    Video video;

    Page<Category> categoryPage;


    @BeforeEach
    void setUp() {
        video = new Video(VIDEO_TITLE, VIDEO_DESCRIPTION, VIDEO_URL);
        category = new Category(CATEGORY, COLOR);
        category.setId(CATEGORY_ID);
        category.getVideo().add(video);
        categoryForm = new CategoryForm(CATEGORY, COLOR);
        categoryPage = new PageImpl<>(List.of(category));
    }

    @Test
    void should_create_category_successfully() {

        when(repository.save(any(Category.class))).thenReturn(category);

        Category response = service.create(categoryForm);

        assertEquals(category, response);
        assertEquals(CATEGORY_ID, response.getId());
        assertEquals(CATEGORY, response.getTitle());
        assertEquals(COLOR, response.getColor());
    }

    @Test
    void should_return_all_categories() {
        when(repository.findAll(any(Pageable.class)))
                .thenReturn(categoryPage);

        List<CategoryDto> response = service.findAll(Pageable.ofSize(10)).stream().toList();

        assertEquals(CategoryDto.class, response.get(0).getClass());
        assertEquals(1, response.size());
        assertEquals(CATEGORY_ID, response.get(0).getId());
        assertEquals(CATEGORY, response.get(0).getTitle());
        assertEquals(COLOR, response.get(0).getColor());
    }

    @Test
    void should_return_category_by_id() {
        when(repository.findById(any(Long.class))).thenReturn(java.util.Optional.of(category));

        CategoryDto response = service.findById(CATEGORY_ID);

        assertEquals(CategoryDto.class, response.getClass());
        assertEquals(CATEGORY_ID, response.getId());
        assertEquals(CATEGORY, response.getTitle());
        assertEquals(COLOR, response.getColor());
    }

    @Test
    void should_throw_exception_when_category_not_found_when_find_by_id() {
        when(repository.findById(any(Long.class))).thenReturn(java.util.Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> service.findById(CATEGORY_ID));
    }

    @Test
    void should_find_by_id_with_video(){
        when(repository.findById(any(Long.class))).thenReturn(java.util.Optional.of(category));

        CategoryWithVideoDto response = service.findByIdWithVideos(CATEGORY_ID);

        assertEquals(CategoryWithVideoDto.class, response.getClass());
        assertEquals(CATEGORY_ID, response.getId());
        assertEquals(CATEGORY, response.getTitle());
        assertEquals(COLOR, response.getColor());
        assertEquals(1, response.getVideos().size());
        assertEquals(VIDEO_TITLE, response.getVideos().get(0).getTitle());
        assertEquals(VIDEO_DESCRIPTION, response.getVideos().get(0).getDescription());
        assertEquals(VIDEO_URL, response.getVideos().get(0).getUrl());
    }

    @Test
    void should_throw_exception_when_category_not_found_when_find_by_id_with_video() {
        when(repository.findById(any(Long.class))).thenReturn(java.util.Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> service.findByIdWithVideos(CATEGORY_ID));
    }
    @Test
    void should_update_category_successfully() {
        when(repository.findById(any(Long.class))).thenReturn(java.util.Optional.of(category));
        when(repository.save(any(Category.class))).thenReturn(category);

        CategoryDto response = service.update(CATEGORY_ID, categoryForm);

        assertEquals(CategoryDto.class, response.getClass());
        assertEquals(CATEGORY_ID, response.getId());
        assertEquals(CATEGORY, response.getTitle());
        assertEquals(COLOR, response.getColor());
    }

    @Test
    void should_throw_exception_when_category_not_found_when_update() {
        when(repository.findById(any(Long.class))).thenReturn(java.util.Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> service.update(CATEGORY_ID, categoryForm));
    }

    @Test
    void should_delete_category_successfully() {

        service.delete(CATEGORY_ID);

        verify(repository, times(1)).deleteById(CATEGORY_ID);
    }

    @Test
    void should_throw_exception_when_category_not_found_when_delete() {
        doThrow(CategoryNotFoundException.class).when(repository).deleteById(any(Long.class));

        assertThrows(CategoryNotFoundException.class, () -> service.delete(CATEGORY_ID));
    }
}