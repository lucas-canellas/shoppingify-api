package com.lucasdc.shoppingifyapi.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucasdc.shoppingifyapi.domain.exception.CategoryNotFoundException;
import com.lucasdc.shoppingifyapi.domain.exception.NegocioException;
import com.lucasdc.shoppingifyapi.domain.models.Category;
import com.lucasdc.shoppingifyapi.domain.repositories.CategoryRepository;

import jakarta.transaction.Transactional;

@Service
public class CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public Category save(Category category) {
        Category categorySaved = categoryRepository.findByName(category.getName()).orElse(null);
        
        if (categorySaved != null) {
            throw new NegocioException(String.format("Já existe a categoria %s", category.getName()));
        }

        return categoryRepository.save(category);
    }

    @Transactional
    public void delete(Long id) {
        Category category = searchOrFail(id);
        categoryRepository.delete(category);
    }

    public Category searchOrFail(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
    }


    
}
