package com.gautham.auth.security;

import com.gautham.auth.models.AppRole;
import com.gautham.auth.models.Role;
import com.gautham.auth.models.User;
import com.gautham.auth.repositories.RoleRepository;
import com.gautham.auth.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // 1. Create Roles if they don't exist
        Role userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setRoleName(AppRole.ROLE_USER);
                    return roleRepository.save(newRole);
                });

        Role adminRole = roleRepository.findByRoleName(AppRole.ROLE_ADMIN)
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setRoleName(AppRole.ROLE_ADMIN);
                    return roleRepository.save(newRole);
                });

        // 2. Create a Normal User
        if (userRepository.findByUsername("user1").isEmpty()) {
            User normalUser = new User();
            normalUser.setUsername("user1");
            normalUser.setPassword(passwordEncoder.encode("password123")); // Securely hash the password
            normalUser.setEmail("user1@example.com");
            normalUser.setRole(userRole); // Assign the normal user role
            userRepository.save(normalUser);
            System.out.println("Created normal user: user1 / password123");
        }

        // 3. Create an Admin User
        if (userRepository.findByUsername("admin1").isEmpty()) {
            User adminUser = new User();
            adminUser.setUsername("admin1");
            adminUser.setPassword(passwordEncoder.encode("admin123")); // Securely hash the password
            adminUser.setEmail("admin1@example.com");
            adminUser.setRole(adminRole); // Assign the admin role
            userRepository.save(adminUser);
            System.out.println("Created admin user: admin1 / admin123");
        }
    }
}