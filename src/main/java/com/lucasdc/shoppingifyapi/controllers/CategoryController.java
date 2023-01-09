package com.lucasdc.shoppingifyapi.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucasdc.shoppingifyapi.models.Category;
import com.lucasdc.shoppingifyapi.models.CategoryResumoModel;
import com.lucasdc.shoppingifyapi.models.Item;
import com.lucasdc.shoppingifyapi.models.ItemResumoModel;
import com.lucasdc.shoppingifyapi.repositories.CategoryRepository;
import com.lucasdc.shoppingifyapi.services.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        return ResponseEntity.ok().body(categoryService.save(category));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResumoModel>> findAllCategories() {
        List<CategoryResumoModel> categoryResumoModels = new ArrayList<>();
        List<Category> categories = categoryRepository.findAll();

        for (Category category : categories) {
            CategoryResumoModel categoryResumoModel = new CategoryResumoModel();
            categoryResumoModel.setName(category.getName());
            List<ItemResumoModel> itemResumoModels = new ArrayList<>();
            
            for (Item item : category.getItens()) {
                ItemResumoModel resumoModel = new ItemResumoModel();
                resumoModel.setName(item.getName());
                itemResumoModels.add(resumoModel);                
            }

            categoryResumoModel.setItens(itemResumoModels);
            categoryResumoModels.add(categoryResumoModel);
        }

        return ResponseEntity.ok().body(categoryResumoModels);
        
    }

}
