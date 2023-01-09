package com.lucasdc.shoppingifyapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.lucasdc.shoppingifyapi.models.User;
import com.lucasdc.shoppingifyapi.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
    
        User user = userRepository.findByEmail(currentPrincipalName).orElseThrow();
        
        return user;
    }
}
