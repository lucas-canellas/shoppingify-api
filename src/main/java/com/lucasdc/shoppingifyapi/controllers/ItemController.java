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

import com.lucasdc.shoppingifyapi.dto.input.ItemInput;
import com.lucasdc.shoppingifyapi.dto.output.CategoryOutput;
import com.lucasdc.shoppingifyapi.dto.output.ItemOutput;
import com.lucasdc.shoppingifyapi.models.Category;
import com.lucasdc.shoppingifyapi.models.Item;
import com.lucasdc.shoppingifyapi.repositories.ItemRepository;
import com.lucasdc.shoppingifyapi.services.CategoryService;
import com.lucasdc.shoppingifyapi.services.ItemService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/items")
public class ItemController {
    
    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<ItemOutput> findAll() {
        return itemRepository.findAll().stream().map(this::toOutput).toList();
    }

    @GetMapping("/{itemId}")
    public ItemOutput find(@PathVariable Long itemId) {
        Item item = itemService.searchOrFail(itemId);       
        return toOutput(item);
    }

    @PostMapping
    public ResponseEntity<ItemOutput> save(@RequestBody @Valid ItemInput itemInput) {
        
        Item item = toDomainObject(itemInput);   
        ItemOutput itemOutput = toOutput(itemService.save(item)); 

        return ResponseEntity.ok(itemOutput);
    }

    @PutMapping("/{itemId}")
    public ItemOutput update(@PathVariable Long itemId, @RequestBody @Valid ItemInput itemInput) {
        Item item = toDomainObject(itemInput);
        return toOutput(itemService.save(item));
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> delete(@PathVariable Long itemId) {
        itemService.delete(itemId);
        return ResponseEntity.noContent().build();
    }

    private ItemOutput toOutput(Item item) {
        ItemOutput itemOutput = new ItemOutput();
        itemOutput.setId(item.getId());
        itemOutput.setName(item.getName());
        itemOutput.setImage(item.getImage());
        itemOutput.setNote(item.getNote());
        
        CategoryOutput categoryOutput = new CategoryOutput();
        categoryOutput.setId(item.getCategory().getId());
        categoryOutput.setName(item.getCategory().getName());

        itemOutput.setCategory(categoryOutput);

        return itemOutput;
    }

    private Item toDomainObject(ItemInput itemInput) {
        Item item = new Item();
        item.setName(itemInput.getName());
        item.setImage(itemInput.getImage());
        item.setNote(itemInput.getNote());

        Category category = categoryService.searchOrFail(itemInput.getCategory().getId());

        item.setCategory(category);
                
        return item;
    }




}