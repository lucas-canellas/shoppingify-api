package com.lucasdc.shoppingifyapi.dto.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartInput {

    @NotBlank
    private String name;
}
