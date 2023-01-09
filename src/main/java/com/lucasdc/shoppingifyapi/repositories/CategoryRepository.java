package com.lucasdc.shoppingifyapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucasdc.shoppingifyapi.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {    
}
