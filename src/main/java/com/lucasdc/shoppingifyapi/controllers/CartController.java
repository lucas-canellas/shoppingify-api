package com.lucasdc.shoppingifyapi.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucasdc.shoppingifyapi.dto.input.CartInput;
import com.lucasdc.shoppingifyapi.dto.output.CartOutput;
import com.lucasdc.shoppingifyapi.dto.output.ItemCartOutput;
import com.lucasdc.shoppingifyapi.dto.output.ItemResumoOutput;
import com.lucasdc.shoppingifyapi.dto.output.UserOutput;
import com.lucasdc.shoppingifyapi.models.Cart;
import com.lucasdc.shoppingifyapi.models.Item;
import com.lucasdc.shoppingifyapi.models.ItemCart;
import com.lucasdc.shoppingifyapi.models.StatusCart;
import com.lucasdc.shoppingifyapi.models.User;
import com.lucasdc.shoppingifyapi.repositories.CartRepository;
import com.lucasdc.shoppingifyapi.repositories.ItemRepository;
import com.lucasdc.shoppingifyapi.repositories.UserRepository;
import com.lucasdc.shoppingifyapi.services.CartService;
import com.lucasdc.shoppingifyapi.services.ItemCartService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/carts")
public class CartController {
    
    @Autowired
    private CartService cartService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemCartService itemCartService;

    @GetMapping
    public ResponseEntity<List<CartOutput>> getAllCarts() {
        return ResponseEntity.ok(cartRepository.findAll().stream().map(this::toOutput).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartOutput> getCartById(@PathVariable Long id) {
        Optional<Cart> cart = cartRepository.findById(id);
        if (cart.isPresent()) {
            return ResponseEntity.ok(toOutput(cart.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Void> createCart(@Valid @RequestBody CartInput cartInput) {

        Cart cart = toDomainObject(cartInput);
        
        cart = cartService.save(cart);        
        
        return ResponseEntity.ok().build();
    }

    @PostMapping("/add/{id}")
    public ResponseEntity<CartOutput> addItemCart(@PathVariable Long id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).get();

        Cart cart = cartRepository.findByStatusAndUser(StatusCart.ACTIVE, user.getId()).get();
        Item item = itemRepository.findById(id).get();
        
        ItemCart itemCart = new ItemCart();
        itemCart.setItem(item);
        itemCart.setCart(cart);
        itemCart.setQuantity(1);

        itemCart = itemCartService.save(itemCart); 
        
        return ResponseEntity.ok(toOutput(cart));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long id) {
        Optional<Cart> cart = cartRepository.findById(id);
        if (cart.isPresent()) {
            cartRepository.delete(cart.get());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private CartOutput toOutput(Cart cart) {
        CartOutput cartOutput = new CartOutput();
        cartOutput.setName(cart.getName());
        cartOutput.setStatus(cart.getStatus());
        UserOutput userOutput = new UserOutput();
        userOutput.setId(cart.getUser().getId());
        userOutput.setEmail(cart.getUser().getEmail());
        cartOutput.setUser(userOutput);

        List<ItemCartOutput> items = new ArrayList<>();
        for (ItemCart itemCart : cart.getItems()) {
            ItemCartOutput itemCartOutput = new ItemCartOutput();
            itemCartOutput.setId(itemCart.getId());
            itemCartOutput.setQuantity(itemCart.getQuantity());
            
            ItemResumoOutput itemResumoOutput = new ItemResumoOutput();
            itemResumoOutput.setId(itemCart.getItem().getId());
            itemResumoOutput.setName(itemCart.getItem().getName());
            itemResumoOutput.setCategory(itemCart.getItem().getCategory());
            
            itemCartOutput.setItem(itemResumoOutput);

            items.add(itemCartOutput);
        }   

        cartOutput.setItems(items);
        
        return cartOutput;
    }

    private Cart toDomainObject(CartInput cartInput) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        Cart cart = new Cart();
        cart.setName(cartInput.getName());
        cart.setStatus(StatusCart.ACTIVE);
        Optional<User> user = userRepository.findByEmail(currentPrincipalName);
        cart.setUser(user.get());

        return cart;
    }

    

}
