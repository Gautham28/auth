package com.gautham.auth.controllers;

import com.gautham.auth.models.User;
import com.gautham.auth.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    // The @PreAuthorize annotation intercepts the request BEFORE the method runs.
    // It checks if the currently logged-in user has the "ROLE_ADMIN" authority.
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users")
    public List<User> getAllUsers() {
        // If they are an admin, return a list of every user in the database
        return userRepository.findAll();
    }
}