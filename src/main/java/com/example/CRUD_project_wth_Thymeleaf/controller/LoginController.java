package com.example.CRUD_project_wth_Thymeleaf.controller; // Package declaration

import org.springframework.stereotype.Controller; // Marks this class as a Spring MVC controller.
import org.springframework.web.bind.annotation.GetMapping; // Imports @GetMapping annotation.

@Controller // Indicates that this class is a Spring MVC Controller.
public class LoginController {

    @GetMapping("/login") // Handles GET requests to the `/login` URL.
    public String login() { // Method to display the login page.
        return "login"; // Returns the name of the Thymeleaf template (`login.html`) to be rendered.
    }
}