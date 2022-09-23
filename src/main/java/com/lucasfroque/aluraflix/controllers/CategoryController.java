package com.lucasfroque.aluraflix.controllers;

import com.lucasfroque.aluraflix.dto.request.CategoryForm;
import com.lucasfroque.aluraflix.dto.response.CategoryDto;
import com.lucasfroque.aluraflix.entities.Category;
import com.lucasfroque.aluraflix.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @PostMapping
    public ResponseEntity<CategoryDto> create(@RequestBody @Valid CategoryForm categoryForm){
       Category category = service.create(categoryForm);
       CategoryDto categoryDto = new CategoryDto(category);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(category).toUri();

        return ResponseEntity.created(uri).body(categoryDto);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> listAll(){
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryDto> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(service.findById(id));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CategoryDto> update(@PathVariable Long id, @RequestBody @Valid CategoryForm categoryForm){
        return ResponseEntity.ok().body(service.update(id, categoryForm));
    }
}
