package com.lucasdc.shoppingifyapi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucasdc.shoppingifyapi.models.Cart;
import com.lucasdc.shoppingifyapi.models.Item;
import com.lucasdc.shoppingifyapi.models.ItemCart;

public interface ItemCartRepository extends JpaRepository<ItemCart, Long> {

    Optional<ItemCart> findByItemAndCart(Item item, Cart cart);


}
