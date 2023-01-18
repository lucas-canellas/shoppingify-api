package com.lucasdc.shoppingifyapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucasdc.shoppingifyapi.exception.CartNotFoundException;
import com.lucasdc.shoppingifyapi.models.Cart;
import com.lucasdc.shoppingifyapi.repositories.CartRepository;

@Service
public class CartService {
    
    @Autowired
    private CartRepository cartRepository;

    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    public void delete(Long cartId) {
        Cart cart = searchOrFail(cartId);
        cartRepository.delete(cart);
    }

    public Cart searchOrFail(Long cartId) {
        return cartRepository.findById(cartId).orElseThrow(() -> new CartNotFoundException(cartId));
    }

}
