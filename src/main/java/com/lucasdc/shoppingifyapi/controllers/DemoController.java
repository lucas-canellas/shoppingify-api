package com.lucasdc.shoppingifyapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucasdc.shoppingifyapi.models.User;
import com.lucasdc.shoppingifyapi.repositories.UserRepository;

@RestController
@RequestMapping("/api/v1/demo-controller")
public class DemoController {

    @Autowired
    private UserRepository userRepo;

    @GetMapping
    public ResponseEntity<String> sayHello() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        User user = userRepo.findByEmail(currentPrincipalName).orElseThrow();

        
        return ResponseEntity.ok(user.id.toString());
    }
}
