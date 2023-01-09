package com.lucasdc.shoppingifyapi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lucasdc.shoppingifyapi.models.Cart;
import com.lucasdc.shoppingifyapi.models.User;

public interface CartRepository extends JpaRepository<Cart, Long> {   
    //find cart by user and active
    @Query("SELECT c FROM Cart c WHERE c.user = ?1 AND c.status = 'ACTIVE'")
    Optional<Cart> findCartByUserAndActive(User user);
}
