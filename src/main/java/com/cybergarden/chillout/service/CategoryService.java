package com.cybergarden.chillout.service;

import com.cybergarden.chillout.model.Category;
import com.cybergarden.chillout.repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category findCategoryByName(String name) {
        return categoryRepository.getCategoryByName(name);
    }

    public ResponseEntity<?> createCategory(String name) {
        Category category = new Category(name);
        if (categoryRepository.getCategoryByName(name) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Category already exists");
        } else {
            categoryRepository.save(category);
            return ResponseEntity.status(HttpStatus.CREATED).body("Category created successfully");
        }
    }

}
