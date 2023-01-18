package com.lucasdc.shoppingifyapi.models;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Cart {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;

    @Enumerated(EnumType.STRING)
    public StatusCart status;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    public List<ItemCart> items;

    public LocalDateTime created_at;
    
    public LocalDateTime updated_at;

    public void addItem(ItemCart item) {
        this.items.add(item);
    }

    public void removeItem(ItemCart item) {
        this.items.remove(item);
    }

    public void updateStatus(StatusCart status) {
        this.status = status;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void setQuantityItem(ItemCart item, int quantity) {
        item.setQuantity(quantity);
    }


}
