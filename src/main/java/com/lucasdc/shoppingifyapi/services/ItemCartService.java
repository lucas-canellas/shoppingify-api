package com.lucasdc.shoppingifyapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucasdc.shoppingifyapi.models.ItemCart;
import com.lucasdc.shoppingifyapi.repositories.ItemCartRepository;

@Service
public class ItemCartService {
    
    @Autowired
    private ItemCartRepository itemCartRepository;

    public ItemCart save(ItemCart itemCart) {
        return itemCartRepository.save(itemCart);
    }

    public void delete(ItemCart itemCart) {
        itemCartRepository.delete(itemCart);
    }



}
