package com.lucasfroque.aluraflix.services;

import com.lucasfroque.aluraflix.dto.request.CategoryForm;
import com.lucasfroque.aluraflix.dto.response.CategoryDto;
import com.lucasfroque.aluraflix.entities.Category;
import com.lucasfroque.aluraflix.exceptions.CategoryNotFoundException;
import com.lucasfroque.aluraflix.respositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository repository;

    public Category create(CategoryForm categoryForm){
        return repository.save(categoryForm.toCategory());
    }
    public List<CategoryDto> findAll(){
        return repository.findAll()
                .stream()
                .map(CategoryDto::new).toList();
    }
    public CategoryDto findById(Long id){
        return repository.findById(id)
                .map(CategoryDto::new)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }
}
