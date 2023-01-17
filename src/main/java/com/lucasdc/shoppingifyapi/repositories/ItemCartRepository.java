package com.lucasdc.shoppingifyapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lucasdc.shoppingifyapi.models.ItemCart;

public interface ItemCartRepository extends JpaRepository<ItemCart, Long> {

}
