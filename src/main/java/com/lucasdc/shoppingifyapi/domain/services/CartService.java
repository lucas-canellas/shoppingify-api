package com.lucasdc.shoppingifyapi.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucasdc.shoppingifyapi.domain.exception.CartNotFoundException;
import com.lucasdc.shoppingifyapi.domain.models.Cart;
import com.lucasdc.shoppingifyapi.domain.models.Item;
import com.lucasdc.shoppingifyapi.domain.models.ItemCart;
import com.lucasdc.shoppingifyapi.domain.models.StatusCart;
import com.lucasdc.shoppingifyapi.domain.repositories.CartRepository;
import com.lucasdc.shoppingifyapi.domain.repositories.ItemCartRepository;

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

    public ItemCart updateQuantity(Cart cart, Item item, Integer quantity) {
        ItemCart itemCart = itemCartRepository.findByItemAndCart(item, cart).orElseThrow(() -> new CartNotFoundException(cart.getId()));
        itemCart.setQuantity(quantity);
        itemCartService.save(itemCart);

        return itemCart;
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
