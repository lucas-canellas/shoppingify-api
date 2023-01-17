package com.lucasdc.shoppingifyapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lucasdc.shoppingifyapi.models.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {   
}
