package com.lucasdc.shoppingifyapi.api.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucasdc.shoppingifyapi.api.dto.input.CartInput;
import com.lucasdc.shoppingifyapi.api.dto.output.CartOutput;
import com.lucasdc.shoppingifyapi.api.dto.output.ItemCartOutput;
import com.lucasdc.shoppingifyapi.api.dto.output.ItemResumoOutput;
import com.lucasdc.shoppingifyapi.api.dto.output.UserOutput;
import com.lucasdc.shoppingifyapi.core.security.auth.AuthenticationService;
import com.lucasdc.shoppingifyapi.domain.models.Cart;
import com.lucasdc.shoppingifyapi.domain.models.Item;
import com.lucasdc.shoppingifyapi.domain.models.ItemCart;
import com.lucasdc.shoppingifyapi.domain.models.StatusCart;
import com.lucasdc.shoppingifyapi.domain.models.User;
import com.lucasdc.shoppingifyapi.domain.repositories.CartRepository;
import com.lucasdc.shoppingifyapi.domain.repositories.ItemCartRepository;
import com.lucasdc.shoppingifyapi.domain.repositories.ItemRepository;
import com.lucasdc.shoppingifyapi.domain.services.CartService;
import com.lucasdc.shoppingifyapi.domain.services.ItemCartService;
import com.lucasdc.shoppingifyapi.domain.services.ItemService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/carts")
public class CartController {
    
    @Autowired
    private CartService cartService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemCartService itemCartService;

    @Autowired
    private ItemCartRepository itemCartRepository;

    @Autowired
    private ItemService itemService;

    @GetMapping
    public ResponseEntity<List<CartOutput>> getAllCarts() {
        return ResponseEntity.ok(cartRepository.findAll().stream().map(this::toOutput).toList());
    }

    @GetMapping("/{id}")
    public CartOutput getCartById(@PathVariable Long id) {
        Cart cart = cartService.searchOrFail(id);

        return toOutput(cart);
    }

    @PostMapping
    public ResponseEntity<Void> createCart(@Valid @RequestBody CartInput cartInput) {

        Cart cart = toDomainObject(cartInput);
        
        cart = cartService.save(cart);        
        
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update-name")
    public ResponseEntity<CartOutput> updateCartName(@Valid @RequestBody CartInput cartInput) {
        
        User user = authenticationService.getUser();

        Cart cart = cartRepository.findByStatusAndUser(StatusCart.ACTIVE, user.getId()).orElse(null);

        if(cart == null) {
            Cart cart2 = new Cart();
            cart2.setName(cartInput.getName());
            cart2.setStatus(StatusCart.ACTIVE);

            cart2.setUser(user);
            cartService.save(cart2);
            
            return ResponseEntity.ok().build();
        }

        cart.setName(cartInput.getName());

        cart = cartService.save(cart);

        return ResponseEntity.ok(toOutput(cart));
    }

    @PostMapping("/add/{itemId}")
    public ResponseEntity<ItemCartOutput> addItemCart(@PathVariable Long itemId) {

        Item item = itemService.searchOrFail(itemId);

        ItemCart itemCart = itemCartService.create(item); 
        
        return ResponseEntity.ok(toItemCartOutput(itemCart));
    }


    
    @PostMapping("/remove/{id}")
    public ResponseEntity<Void> removeItemCart(@PathVariable Long id) {

        User user = authenticationService.getUser();

        Cart cart = cartRepository.findByStatusAndUser(StatusCart.ACTIVE, user.getId()).get();
        Item item = itemRepository.findById(id).get();

        ItemCart itemCart = itemCartRepository.findByItemAndCart(item, cart).get();

        itemCartService.delete(itemCart);

        return ResponseEntity.ok().build();
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

    @PutMapping("/item/{itemId}/quantity/{quantity}")
    public ResponseEntity<ItemCartOutput> updateItemCartQuantity(@PathVariable Long itemId, @PathVariable Integer quantity) {
        User user = authenticationService.getUser();

        Cart cart = cartRepository.findByStatusAndUser(StatusCart.ACTIVE, user.getId()).get();
        Item item = itemRepository.findById(itemId).get();

        ItemCart itemCart = cartService.updateQuantity(cart, item, quantity);
        
        return ResponseEntity.ok(toItemCartOutput(itemCart));
    }

    @PostMapping("/complete")
    public ResponseEntity<Void> completeCart() {
        User user = authenticationService.getUser();

        Cart cart = cartRepository.findByStatusAndUser(StatusCart.ACTIVE, user.getId()).get();

        cartService.statusCompleted(cart);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/cancel")
    public ResponseEntity<Void> cancelCart() {
        User user = authenticationService.getUser();

        Cart cart = cartRepository.findByStatusAndUser(StatusCart.ACTIVE, user.getId()).get();

        cartService.statusCanceled(cart);

        return ResponseEntity.ok().build();
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
            ItemCartOutput itemCartOutput = toItemCartOutput(itemCart);

            items.add(itemCartOutput);
        }   

        cartOutput.setItems(items);
        
        return cartOutput;
    }

    private ItemCartOutput toItemCartOutput(ItemCart itemCart) {
        ItemCartOutput itemCartOutput = new ItemCartOutput();
        itemCartOutput.setId(itemCart.getId());
        itemCartOutput.setQuantity(itemCart.getQuantity());
        
        ItemResumoOutput itemResumoOutput = new ItemResumoOutput();
        itemResumoOutput.setId(itemCart.getItem().getId());
        itemResumoOutput.setName(itemCart.getItem().getName());
        itemResumoOutput.setCategory(itemCart.getItem().getCategory());
        
        itemCartOutput.setItem(itemResumoOutput);
        return itemCartOutput;
    }

    private Cart toDomainObject(CartInput cartInput) {

        Cart cart = new Cart();
        cart.setName(cartInput.getName());
        cart.setStatus(StatusCart.ACTIVE);
        User user = authenticationService.getUser();
        cart.setUser(user);

        return cart;
    }

    

}
