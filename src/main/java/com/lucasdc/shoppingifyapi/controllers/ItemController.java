package com.lucasdc.shoppingifyapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucasdc.shoppingifyapi.models.Item;
import com.lucasdc.shoppingifyapi.models.outputs.CategoryIdModel;
import com.lucasdc.shoppingifyapi.models.outputs.ItemModel;
import com.lucasdc.shoppingifyapi.services.ItemService;

@RestController
@RequestMapping("/api/itens")
public class ItemController {
    
    @Autowired
    private ItemService itemService;

    @PostMapping
    public ResponseEntity<ItemModel> create(@RequestBody Item item) {
        itemService.save(item);
        
        ItemModel itemModel = new ItemModel();
        itemModel.setId(item.getId());
        itemModel.setName(item.getName());
        itemModel.setNote(item.getNote());
        itemModel.setImage(item.getImage());
        CategoryIdModel categoryIdModel = new CategoryIdModel();
        categoryIdModel.setId(item.getCategory().getId());
        itemModel.setCategory(categoryIdModel);
        

        return ResponseEntity.ok().body(itemModel);
    }

}
