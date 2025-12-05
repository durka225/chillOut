package com.cybergarden.chillout.controller.category;

import com.cybergarden.chillout.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/category/new")
    ResponseEntity<?> newCategory(
            @RequestParam String categoryName
    ) {
        return categoryService.createCategory(categoryName);
    }
}
