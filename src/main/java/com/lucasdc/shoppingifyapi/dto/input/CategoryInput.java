package com.lucasdc.shoppingifyapi.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryInput {

    @NotBlank
    private String name;
}
