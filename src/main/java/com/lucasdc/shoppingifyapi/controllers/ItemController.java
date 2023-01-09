package com.lucasdc.shoppingifyapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucasdc.shoppingifyapi.models.Item;
import com.lucasdc.shoppingifyapi.services.ItemService;

@RestController
@RequestMapping("/api/itens")
public class ItemController {
    
    @Autowired
    private ItemService itemService;

    @PostMapping
    public ResponseEntity<Item> create(@RequestBody Item item) {
        return ResponseEntity.ok().body(itemService.save(item));
    }

}
