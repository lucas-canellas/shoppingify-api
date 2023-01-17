package com.lucasdc.shoppingifyapi.dto.output;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemOutput {
    
    private Long id;

    private String name;

    private String note;

    private String image;

    private CategoryOutput category;

}
