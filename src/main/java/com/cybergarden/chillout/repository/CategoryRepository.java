package com.cybergarden.chillout.repository;

import com.cybergarden.chillout.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category getCategoryByName(String name);

    // Все доступные пользователю категории:
    @Query("""
        SELECT c FROM categories c
        WHERE c.isDefault = true OR c.user.id = :userId
    """)
    List<Category> findAvailableForUser(UUID userId);
}
