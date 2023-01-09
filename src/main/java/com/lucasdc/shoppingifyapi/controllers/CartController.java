package com.lucasdc.shoppingifyapi.controllers;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucasdc.shoppingifyapi.models.Cart;
import com.lucasdc.shoppingifyapi.models.StatusCart;
import com.lucasdc.shoppingifyapi.models.User;
import com.lucasdc.shoppingifyapi.models.inputs.CartInput;
import com.lucasdc.shoppingifyapi.models.outputs.CartModel;
import com.lucasdc.shoppingifyapi.models.outputs.UserIdModel;
import com.lucasdc.shoppingifyapi.repositories.CartRepository;
import com.lucasdc.shoppingifyapi.services.CartService;
import com.lucasdc.shoppingifyapi.services.UserService;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserService userService;
    
    @PostMapping
    public CartModel createOrEditCart(@RequestBody CartInput cartInput) {

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
        cartService.save(activeCart);

        CartModel cartModel = new CartModel();
        cartModel.setName(activeCart.getName());
        cartModel.setStatus(activeCart.getStatus());
        UserIdModel userIdModel = new UserIdModel();
        userIdModel.setId(activeCart.getUser().getId());
        cartModel.setUser(userIdModel);

        return cartModel; 

    }

    @PostMapping("/completed")
    public void completeCart() {
        User user = userService.getUserAuthenticated();
        Cart activeCart = cartRepository.findCartByUserAndActive(user).orElseThrow(
            () -> new RuntimeException("Cart not found")
        );
        activeCart.setStatus(StatusCart.COMPLETED);
        activeCart.setCreated_at(LocalDateTime.now());
        cartService.save(activeCart);
    }

    @PostMapping("/canceled")
    public void cancelCart() {
        User user = userService.getUserAuthenticated();
        Cart activeCart = cartRepository.findCartByUserAndActive(user).orElseThrow(
            () -> new RuntimeException("Cart not found")
        );
        activeCart.setStatus(StatusCart.CANCELED);
        activeCart.setCreated_at(LocalDateTime.now());
        cartService.save(activeCart);
    }
    

}
