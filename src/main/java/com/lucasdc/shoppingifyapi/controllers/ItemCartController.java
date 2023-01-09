package com.lucasdc.shoppingifyapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucasdc.shoppingifyapi.models.ItemCart;
import com.lucasdc.shoppingifyapi.services.ItemCartService;

@RestController
@RequestMapping("/api/itens-cart")
public class ItemCartController {
    
    @Autowired
    private ItemCartService itemCartService;

    @PostMapping
    public void saveAllItensCart(@RequestBody List<ItemCart> itensCart) {
        itensCart.forEach(itemCart -> itemCartService.save(itemCart));
    }
    
}
