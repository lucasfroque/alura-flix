package com.lucasfroque.aluraflix.services;

import com.lucasfroque.aluraflix.dto.request.CategoryForm;
import com.lucasfroque.aluraflix.dto.response.CategoryDto;
import com.lucasfroque.aluraflix.dto.response.CategoryWithVideoDto;
import com.lucasfroque.aluraflix.entities.Category;
import com.lucasfroque.aluraflix.exceptions.CategoryNotFoundException;
import com.lucasfroque.aluraflix.exceptions.VideoNotFoundException;
import com.lucasfroque.aluraflix.respositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
    public CategoryWithVideoDto findByIdWithVideos(Long id){
        return repository.findById(id)
                .map(CategoryWithVideoDto::new)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }
    public CategoryDto update(Long id, CategoryForm categoryForm){
        Category category = repository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
        category.setTitle(categoryForm.getTitle());
        category.setColor(categoryForm.getColor());
        return new CategoryDto(repository.save(category));
    }
    public void delete(Long id){
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new VideoNotFoundException(id);
        }
    }
}
