package com.lucasdc.shoppingifyapi.dto.output;

import java.util.List;

import com.lucasdc.shoppingifyapi.models.StatusCart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartOutput {
    private String name;
    private StatusCart status;
    private UserOutput user;
    List<ItemCartOutput> items;
}
