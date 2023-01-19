package com.lucasdc.shoppingifyapi.domain.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucasdc.shoppingifyapi.domain.exception.ItemNotFoundException;
import com.lucasdc.shoppingifyapi.domain.exception.NegocioException;
import com.lucasdc.shoppingifyapi.domain.models.Category;
import com.lucasdc.shoppingifyapi.domain.models.Item;
import com.lucasdc.shoppingifyapi.domain.repositories.ItemRepository;

import jakarta.persistence.EntityManager;

import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private EntityManager manager;

    @Transactional
    public Item save(Item item) {
        manager.detach(item);

        Optional<Item> itemSaved = itemRepository.findByName(item.getName());

        if (itemSaved.isPresent() && !itemSaved.get().equals(item) && itemSaved.get().getId() != item.getId()) {
            throw new NegocioException(String.format("Já existe o item %s", item.getName()));
        }

        Category category = categoryService.searchOrFail(item.getCategory().getId());
        
        item.setCategory(category);
        
        return itemRepository.save(item);
    }
    
    @Transactional
    public void delete(Long itemId) {
        Item item = searchOrFail(itemId);
        itemRepository.delete(item);
    }

    public Item searchOrFail(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException(itemId));
    }

}
