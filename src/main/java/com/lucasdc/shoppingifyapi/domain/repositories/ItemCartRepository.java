package com.lucasdc.shoppingifyapi.domain.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucasdc.shoppingifyapi.domain.models.Cart;
import com.lucasdc.shoppingifyapi.domain.models.Item;
import com.lucasdc.shoppingifyapi.domain.models.ItemCart;

public interface ItemCartRepository extends JpaRepository<ItemCart, Long> {

    Optional<ItemCart> findByItemAndCart(Item item, Cart cart);

    boolean existsByItemAndCart(Item item, Cart cart);


}
