package com.example.CRUD_project_wth_Thymeleaf.config; // Package declaration

import org.springframework.context.annotation.Bean; // Used to declare a method as a producer of a bean managed by the Spring container
import org.springframework.context.annotation.Configuration; // Indicates that a class declares one or more @Bean methods and may be processed by the Spring container to generate bean definitions and service requests for those beans at runtime.
import org.springframework.security.config.annotation.web.builders.HttpSecurity; // Used to configure HttpSecurity for web-based security.
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity; // Enables Spring Security's web security features.
import org.springframework.security.core.userdetails.User; // Represents an authenticated user (part of Spring Security's core user details)
import org.springframework.security.core.userdetails.UserDetails; // Interface representing core user information.
import org.springframework.security.core.userdetails.UserDetailsService; // Core interface which loads user-specific data.
import org.springframework.security.provisioning.InMemoryUserDetailsManager; // A UserDetailsService implementation that stores user details in memory.
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // A password encoder that uses the BCrypt hashing algorithm.
import org.springframework.security.crypto.password.PasswordEncoder; // Interface for encoding passwords.
import org.springframework.security.web.SecurityFilterChain; // Defines the chain of filters that Spring Security will apply to incoming requests.

import org.springframework.boot.autoconfigure.security.servlet.PathRequest; // Utility for creating RequestMatchers for common Spring Boot paths (like H2 Console).

@Configuration // Marks this class as a source of bean definitions.
@EnableWebSecurity // Enables Spring Security's web security features.
public class SecurityConfig {

    @Bean // Declares this method as a Spring bean, managing the security filter chain.
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { // Takes HttpSecurity object to configure security settings.
        http
            .authorizeHttpRequests(authorizeRequests -> // Starts configuration for request authorization.
                authorizeRequests
                    // Permit all access to the H2 console path first (Crucial for H2)
                    .requestMatchers(PathRequest.toH2Console()).permitAll() // Allows anyone to access the H2 console path. `PathRequest.toH2Console()` is a Spring Boot utility to match the H2 console URL pattern.
                    // Permit all access to the login page
                    .requestMatchers("/login").permitAll() // Allows anyone to access the `/login` URL.
                    // Require ADMIN role for create, update, delete operations
                    .requestMatchers("/products/new", "/products/save", "/products/edit/**", "/products/delete/**").hasRole("ADMIN") // Only users with the "ADMIN" role can access these paths. `**` is a wildcard for any sub-path.
                    // Require USER or ADMIN role for viewing products and the home page
                    .requestMatchers("/products", "/").hasAnyRole("USER", "ADMIN") // Users with either "USER" or "ADMIN" role can access `/products` and the root (`/`).
                    // All other requests must be authenticated
                    .anyRequest().authenticated() // Any other request not explicitly matched above requires the user to be authenticated.
            )
            .formLogin(formLogin -> // Configures form-based login.
                formLogin
                    .loginPage("/login") // Specifies the URL for the custom login page.
                    .defaultSuccessUrl("/products", true) // Redirects to `/products` after successful login. `true` forces the redirect even if the user tried to access a specific page before logging in.
                    .permitAll() // Allows everyone to access the login form.
            )
            .logout(logout -> // Configures logout functionality.
                logout
                    .logoutUrl("/logout") // Specifies the URL to trigger logout (e.g., POST request to /logout).
                    .logoutSuccessUrl("/login?logout") // Redirects to `/login?logout` after successful logout.
                    .permitAll() // Allows everyone to perform logout.
            )
            .csrf(csrf -> csrf // Configures Cross-Site Request Forgery (CSRF) protection.
                // Disable CSRF protection for the H2 console
                .ignoringRequestMatchers(PathRequest.toH2Console()) // Excludes the H2 console path from CSRF protection, necessary for it to function correctly.
            )
            .headers(headers -> headers // Configures security headers.
                // Allow H2 console to be displayed in a frame
                .frameOptions(frameOptions -> frameOptions.sameOrigin()) // Configures X-Frame-Options header to allow the H2 console to be displayed within a frame on the same origin (important for H2's web UI).
            );
        return http.build(); // Builds and returns the configured SecurityFilterChain bean.
    }

    @Bean // Declares this method as a Spring bean, managing user details.
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) { // Takes PasswordEncoder bean as an argument.
        // Define a 'user' with role 'USER'
        UserDetails user = User.withUsername("user") // Creates a UserDetails object for 'user'.
            .password(passwordEncoder.encode("password")) // Encodes the password "password" using the provided PasswordEncoder.
            .roles("USER") // Assigns the "USER" role.
            .build(); // Builds the UserDetails object.

        // Define an 'admin' with roles 'ADMIN' and 'USER'
        UserDetails admin = User.withUsername("admin") // Creates a UserDetails object for 'admin'.
            .password(passwordEncoder.encode("adminpass")) // Encodes the password "adminpass".
            .roles("ADMIN", "USER") // Assigns both "ADMIN" and "USER" roles.
            .build(); // Builds the UserDetails object.

        return new InMemoryUserDetailsManager(user, admin); // Returns an InMemoryUserDetailsManager that manages the in-memory users. This is used for basic, non-database user authentication.
    }

    @Bean // Declares this method as a Spring bean, managing the password encoder.
    public PasswordEncoder passwordEncoder() { // Method to provide the PasswordEncoder.
        return new BCryptPasswordEncoder(); // Returns an instance of BCryptPasswordEncoder, which is a strong password hashing algorithm.
    }
}