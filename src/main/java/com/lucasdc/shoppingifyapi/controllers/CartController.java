package com.lucasdc.shoppingifyapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucasdc.shoppingifyapi.models.Cart;
import com.lucasdc.shoppingifyapi.models.CartInput;
import com.lucasdc.shoppingifyapi.models.StatusCart;
import com.lucasdc.shoppingifyapi.models.User;
import com.lucasdc.shoppingifyapi.repositories.CartRepository;
import com.lucasdc.shoppingifyapi.services.CartService;
import com.lucasdc.shoppingifyapi.services.UserService;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    
/*  String name;
    StatusCart status;
    User user;
    LocalDateTime created_at; */

    @Autowired
    private CartService cartService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserService userService;
    
    @PostMapping
    public Cart createOrEditCart(@RequestBody CartInput cartInput) {

        User user = userService.getUserAuthenticated();
        Cart activeCart = cartRepository.findCartByUserAndActive(user).orElseGet(
            () -> {
                Cart cart = new Cart();
                cart.setName(cartInput.getName());
                cart.setUser(user);
                cart.setStatus(StatusCart.ACTIVE);

                return cartService.save(cart);                
            }
        );

        activeCart.setName(cartInput.getName());

        return cartService.save(activeCart); 

    }

    @PostMapping("/completed")
    public void completeCart() {
        User user = userService.getUserAuthenticated();
        Cart activeCart = cartRepository.findCartByUserAndActive(user).orElseThrow(
            () -> new RuntimeException("Cart not found")
        );
        activeCart.setStatus(StatusCart.COMPLETED);
    }

    @PostMapping("/canceled")
    public void cancelCart() {
        User user = userService.getUserAuthenticated();
        Cart activeCart = cartRepository.findCartByUserAndActive(user).orElseThrow(
            () -> new RuntimeException("Cart not found")
        );
        activeCart.setStatus(StatusCart.CANCELED);
    }
    

}
