package com.lucasdc.shoppingifyapi.api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    
    @GetMapping
    public String hello() {
        return "API SHOPPINGIFY";
    }

}
