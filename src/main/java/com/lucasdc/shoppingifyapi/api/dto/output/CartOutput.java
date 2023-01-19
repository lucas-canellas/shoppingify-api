package com.lucasdc.shoppingifyapi.api.dto.output;

import java.util.List;

import com.lucasdc.shoppingifyapi.domain.models.StatusCart;

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
