package com.lucasfroque.aluraflix.respositories;

import com.lucasfroque.aluraflix.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
