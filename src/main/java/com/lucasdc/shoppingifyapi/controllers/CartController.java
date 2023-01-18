package com.lucasdc.shoppingifyapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucasdc.shoppingifyapi.models.Cart;
import com.lucasdc.shoppingifyapi.services.CartService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/carts")
public class CartController {
    
    @Autowired
    private CartService cartService;

/*     @GetMapping
    private List<CartOutput> findAll() {
        return cartService.findAll().stream().map(this::toOutput).toList();
    }

    @PostMapping
    private ResponseEntity<CartOutput> save(@RequestBody @Valid CartInput cartInput) {
        Cart cart = toDomainObject(cartInput);
        CartOutput cartOutput = toOutput(cartService.save(cart));
        return ResponseEntity.ok(cartOutput);
    }

    @DeleteMapping("/{cartId}")
    private ResponseEntity<Void> delete(@PathVariable Long cartId) {
        cartService.delete(cartId);
        return ResponseEntity.noContent().build();
    } */

    

}
