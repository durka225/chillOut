package com.cybergarden.chillout.controller.category;

import com.cybergarden.chillout.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/category/delete")
    public ResponseEntity<?> deleteCategory(
            @RequestParam String categoryName
    ) {
        return categoryService.delCategory(categoryName);
    }

    @Operation(
            summary = "Получить все категории",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Категории успешно получены",
                            content = @Content(
                                    examples = @ExampleObject(
                                            value = """
                                                [
                                                  "car",
                                                  "clothes",
                                                  "games"
                                                ]
                                                """
                                    )
                            )
                    )
            }
    )
    @GetMapping("/category")
    public ResponseEntity<?> getCategory() {
        return categoryService.getCategories();
    }
}
