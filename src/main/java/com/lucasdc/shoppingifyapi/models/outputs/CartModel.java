package com.lucasdc.shoppingifyapi.models.outputs;

import com.lucasdc.shoppingifyapi.models.StatusCart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartModel {
    private String name;
    private StatusCart status;
    private UserIdModel user;
}
