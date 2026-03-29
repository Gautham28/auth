package com.gautham.auth.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration // Tells Spring Boot that this class contains configuration beans
@EnableWebSecurity // Enables Spring Security's custom web security support
public class SecurityConfig {

    // bcrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean // Tells Spring to manage the object returned by this method as a core component (Bean)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        http
                // 1. URL Authorization Rules
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                // Example: If you wanted a public contact page, you'd allow it here
                                // .requestMatchers("/api/public/**").permitAll()

                                // Enforce that EVERY request to our API must come from a logged-in user
                                .anyRequest().authenticated()
                )

                // 2. Disable CSRF (Cross-Site Request Forgery)
                // CSRF protection is primarily for browsers interacting with standard HTML forms.
                // Since we are building a stateless REST API, we must disable it, or our POST/PUT/DELETE requests will be blocked.
                .csrf(AbstractHttpConfigurer::disable)

                // 3. Stateless Session Management
                // Tells Spring Security NOT to create a session cookie (JSessionID) to remember the user.
                // This forces every single API request to carry its own authentication data.
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // 4. Authentication Type
                // Disables the default HTML login form and enables HTTP Basic Authentication.
                // This expects clients (like Postman) to pass the username/password in a Base64 encoded Header.
                .httpBasic(Customizer.withDefaults());

        // Builds and returns the security filter chain with our new rules
        return http.build();

    }
}




