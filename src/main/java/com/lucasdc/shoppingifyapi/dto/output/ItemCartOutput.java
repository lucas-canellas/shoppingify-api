package com.lucasdc.shoppingifyapi.dto.output;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemCartOutput {
    
    private Long id;
    private ItemResumoOutput item;
    private Integer quantity;

}
