package com.lucasdc.shoppingifyapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucasdc.shoppingifyapi.models.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
    
}
