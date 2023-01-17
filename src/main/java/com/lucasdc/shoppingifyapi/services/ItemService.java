package com.lucasdc.shoppingifyapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucasdc.shoppingifyapi.exception.ItemNotFoundException;
import com.lucasdc.shoppingifyapi.exception.NegocioException;
import com.lucasdc.shoppingifyapi.models.Category;
import com.lucasdc.shoppingifyapi.models.Item;
import com.lucasdc.shoppingifyapi.repositories.ItemRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryService categoryService;

    @Transactional
    public Item save(Item item) {
        Item itemSaved = itemRepository.findByName(item.getName()).orElse(null);

        if(itemSaved != null && !itemSaved.equals(item)) {
            throw new NegocioException("Já existe um item com esse nome");
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
