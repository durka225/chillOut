package com.cybergarden.chillout.components;

import com.cybergarden.chillout.model.Category;
import com.cybergarden.chillout.repository.CategoryRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryDataLoader {

    private final CategoryRepository categoryRepository;

    @PostConstruct
    public void loadDefaults() {
        if (categoryRepository.count() == 0) {
            String[] defaultCategories = {"Nature", "Technology", "Art", "Space", "Animals", "Abstract", "Urban", "People"};
            for (String categoryName : defaultCategories) {
                var category = new Category();
                category.setName(categoryName);
                category.setIsDefault(true);
                categoryRepository.save(category);
            }
        }
    }
}
