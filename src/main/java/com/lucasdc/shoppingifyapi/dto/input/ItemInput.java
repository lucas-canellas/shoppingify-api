package com.lucasdc.shoppingifyapi.dto.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemInput {

    private String name;

    private String note;

    private String image;    

    private CategoryIdInput category;

}
