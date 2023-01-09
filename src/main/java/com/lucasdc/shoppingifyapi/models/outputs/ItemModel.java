package com.lucasdc.shoppingifyapi.models.outputs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemModel {
    private Long id;
    private String name;
    private String note;
    private String image;
    private CategoryIdModel category;
}
