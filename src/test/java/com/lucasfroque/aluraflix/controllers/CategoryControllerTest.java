package com.lucasfroque.aluraflix.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucasfroque.aluraflix.dto.request.CategoryForm;
import com.lucasfroque.aluraflix.dto.response.CategoryDto;
import com.lucasfroque.aluraflix.dto.response.CategoryWithVideoDto;
import com.lucasfroque.aluraflix.entities.Category;
import com.lucasfroque.aluraflix.entities.Video;
import com.lucasfroque.aluraflix.exceptions.CategoryNotFoundException;
import com.lucasfroque.aluraflix.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CategoryController.class)
@ActiveProfiles("test")
class CategoryControllerTest {

    public static final long ID = 1L;
    public static final String TITLE = "Category 1";
    public static final String YELLOW = "yellow";
    Category category;
    CategoryDto categoryDto;
    CategoryForm categoryForm;
    CategoryWithVideoDto categoryWithVideoDto;

    Video video;

    @Autowired
    MockMvc mockMvc;
    @MockBean
    CategoryService service;
    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        video = new Video("Video 1", "Description 1", "http://url.com");
        category = new Category(ID, TITLE, YELLOW, Collections.singletonList(video));
        categoryDto = new CategoryDto(category);
        categoryWithVideoDto = new CategoryWithVideoDto(category);
        categoryForm = new CategoryForm(TITLE, YELLOW);
    }

    @Test
    void when_create_then_return_category_and_status_201() throws Exception {
        when(service.create(any(CategoryForm.class))).thenReturn(category);

        mockMvc.perform(post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryForm)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.title").value(TITLE))
                .andExpect(jsonPath("$.color").value(YELLOW));
    }

    @Test
    void when_create_with_invalid_title_then_return_status_400() throws Exception {
        categoryForm = new CategoryForm("", YELLOW);

        mockMvc.perform(post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryForm)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void when_create_with_invalid_color_then_return_status_400() throws Exception {
        categoryForm = new CategoryForm(TITLE, "");

        mockMvc.perform(post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryForm)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void when_findAll_then_return_list_of_categories_and_status_200() throws Exception {
        when(service.findAll()).thenReturn(Arrays.asList(categoryDto, categoryDto));

        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(ID))
                .andExpect(jsonPath("$[0].title").value(TITLE))
                .andExpect(jsonPath("$[0].color").value(YELLOW))
                .andExpect(jsonPath("$[1].id").value(ID))
                .andExpect(jsonPath("$[1].title").value(TITLE))
                .andExpect(jsonPath("$[1].color").value(YELLOW));
    }

    @Test
    void when_findById_then_return_category_and_status_200() throws Exception {
        when(service.findById(ID)).thenReturn(categoryDto);

        mockMvc.perform(get("/categories/{id}", ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.title").value(TITLE))
                .andExpect(jsonPath("$.color").value(YELLOW));
    }

    @Test
    void when_findById_with_invalid_id_then_return_status_404() throws Exception {
        when(service.findById(ID)).thenThrow(new CategoryNotFoundException(ID));

        mockMvc.perform(get("/categories/{id}", ID))
                .andExpect(status().isNotFound());
    }

    @Test
    void when_findByIdWithVideos_then_return_category_and_status_200() throws Exception {
        when(service.findByIdWithVideos(ID)).thenReturn(categoryWithVideoDto);

        mockMvc.perform(get("/categories/{id}/videos", ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.title").value(TITLE))
                .andExpect(jsonPath("$.color").value(YELLOW));
    }
    @Test
    void when_findByIdWithVideos_with_invalid_id_then_return_status_404() throws Exception {
        when(service.findByIdWithVideos(ID)).thenThrow(new CategoryNotFoundException(ID));

        mockMvc.perform(get("/categories/{id}/videos", ID))
                .andExpect(status().isNotFound());
    }

    @Test
    void when_update_then_return_category_and_status_200() throws Exception {
        when(service.update(any(), any())).thenReturn(categoryDto);

        mockMvc.perform(put("/categories/{id}", ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryForm)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.title").value(TITLE))
                .andExpect(jsonPath("$.color").value(YELLOW));
    }

    @Test
    void when_update_with_invalid_id_then_return_status_404() throws Exception {
        when(service.update(any(), any())).thenThrow(new CategoryNotFoundException(ID));

        mockMvc.perform(put("/categories/{id}", ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryForm)))
                .andExpect(status().isNotFound());
    }
    @Test
    void when_update_with_invalid_title_then_return_status_400() throws Exception {
        categoryForm = new CategoryForm("", YELLOW);

        mockMvc.perform(put("/categories/{id}", ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryForm)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void when_update_with_invalid_color_then_return_status_400() throws Exception {
        categoryForm = new CategoryForm(TITLE, "");

        mockMvc.perform(put("/categories/{id}", ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryForm)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void when_delete_then_return_status_204() throws Exception {
        doNothing().when(service).delete(any());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/categories/{id}", ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void when_delete_with_invalid_id_then_return_status_404() throws Exception {
        doThrow(new CategoryNotFoundException(ID)).when(service).delete(any());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/categories/{id}", ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}