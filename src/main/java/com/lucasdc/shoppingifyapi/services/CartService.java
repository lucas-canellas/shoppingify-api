package com.lucasdc.shoppingifyapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucasdc.shoppingifyapi.exception.CartNotFoundException;
import com.lucasdc.shoppingifyapi.models.Cart;
import com.lucasdc.shoppingifyapi.models.Item;
import com.lucasdc.shoppingifyapi.models.ItemCart;
import com.lucasdc.shoppingifyapi.models.StatusCart;
import com.lucasdc.shoppingifyapi.repositories.CartRepository;
import com.lucasdc.shoppingifyapi.repositories.ItemCartRepository;

@Service
public class CartService {
    
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ItemCartService itemCartService;

    @Autowired
    private ItemCartRepository itemCartRepository;

    public Cart save(Cart cart) {        
        return cartRepository.save(cart);
    }

    public void delete(Long cartId) {
        Cart cart = searchOrFail(cartId);
        cartRepository.delete(cart);
    }

    public void updateQuantity(Cart cart, Item item, Integer quantity) {
        ItemCart itemCart = itemCartRepository.findByItemAndCart(item, cart).orElseThrow(() -> new CartNotFoundException(cart.getId()));
        itemCart.setQuantity(quantity);
        itemCartService.save(itemCart);
    }

    public void statusCompleted(Cart cart) {
        cart.setStatus(StatusCart.COMPLETED);
        cartRepository.save(cart);
    }

    public void statusCanceled(Cart cart) {
        cart.setStatus(StatusCart.CANCELED);
        cartRepository.save(cart);
    }

    public Cart searchOrFail(Long cartId) {
        return cartRepository.findById(cartId).orElseThrow(() -> new CartNotFoundException(cartId));
    }

}
