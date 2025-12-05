package com.cybergarden.chillout.repository;

import com.cybergarden.chillout.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category getCategoryByName(String name);
}
