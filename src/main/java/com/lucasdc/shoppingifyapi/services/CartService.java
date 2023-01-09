package com.lucasdc.shoppingifyapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucasdc.shoppingifyapi.models.Cart;
import com.lucasdc.shoppingifyapi.repositories.CartRepository;

@Service
public class CartService {
    
    @Autowired
    private CartRepository cartRepository;
    
    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }
    

 

    
}
