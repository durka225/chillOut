package com.cybergarden.chillout.service;

import com.cybergarden.chillout.model.Category;
import com.cybergarden.chillout.model.Purchases;
import com.cybergarden.chillout.model.User;
import com.cybergarden.chillout.repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserService userService;

    public CategoryService(CategoryRepository categoryRepository, UserService userService) {
        this.categoryRepository = categoryRepository;
        this.userService = userService;
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

    public ResponseEntity<?> delCategory(String name) {
        Category category = categoryRepository.getCategoryByName(name);
        categoryRepository.delete(category);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<?> getCategories() {
        List<Category> category = categoryRepository.findAll();
        List<String> categoriesResponse = new ArrayList<>();
        category.forEach( category1 -> {
            categoriesResponse.add(category1.getName());
        });
        return ResponseEntity.ok().body(categoriesResponse);
    }

}
