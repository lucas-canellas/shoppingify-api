package com.lucasdc.shoppingifyapi.models;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    public LocalDateTime created_at;
    
    public LocalDateTime updated_at;


}
