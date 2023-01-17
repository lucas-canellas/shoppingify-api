package com.lucasdc.shoppingifyapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucasdc.shoppingifyapi.dto.input.CategoryInput;
import com.lucasdc.shoppingifyapi.dto.output.CategoryOutput;
import com.lucasdc.shoppingifyapi.models.Category;
import com.lucasdc.shoppingifyapi.repositories.CategoryRepository;
import com.lucasdc.shoppingifyapi.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;
    

    @PostMapping()
    public ResponseEntity<CategoryOutput> save(@RequestBody @Valid CategoryInput categoryInput) {
        
        Category category = toDomainObject(categoryInput);   
        CategoryOutput categoryOutput = toOutput(categoryService.save(category)); 

        return ResponseEntity.ok(categoryOutput);
    }

    @GetMapping("/{categoryId}")
    public CategoryOutput find(@PathVariable Long categoryId) {
        Category category = categoryService.searchOrFail(categoryId);       
        
        return toOutput(category);
    }

    @GetMapping
    public List<CategoryOutput> findAll() {
        return categoryRepository.findAll().stream().map(this::toOutput).toList();
    }

    @PutMapping("/{categoryId}")
    public CategoryOutput update(@PathVariable Long categoryId, @RequestBody @Valid CategoryInput categoryInput) {
        Category category = categoryService.searchOrFail(categoryId);
        category.setName(categoryInput.getName());
        return toOutput(categoryService.save(category));
    }

    @DeleteMapping("/{categoryId}")
    public void delete(@PathVariable Long categoryId) {
        categoryService.delete(categoryId);
    }

    private CategoryOutput toOutput(Category category) {
        CategoryOutput categoryOutput = new CategoryOutput();
        categoryOutput.setName(category.getName());
        categoryOutput.setId(category.getId());
        return categoryOutput;
    }

    private Category toDomainObject(CategoryInput categoryInput) {
        Category category = new Category();
        category.setName(categoryInput.getName());
        return category;
    }


        
    


}
