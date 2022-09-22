package com.lucasfroque.aluraflix.services;

import com.lucasfroque.aluraflix.dto.request.CategoryForm;
import com.lucasfroque.aluraflix.entities.Category;
import com.lucasfroque.aluraflix.respositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository repository;

    public Category create(CategoryForm categoryForm){
        return repository.save(categoryForm.toCategory());
    }
}
