package com.lucasdc.shoppingifyapi.models;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResumoModel {
    private String name;
    private List<ItemResumoModel> itens;
}
