package com.example.CRUD_project_wth_Thymeleaf.controller; // Package declaration

import com.example.CRUD_project_wth_Thymeleaf.model.Product; // Imports the Product entity class.
import com.example.CRUD_project_wth_Thymeleaf.service.ProductService; // Imports the ProductService for business logic.
import org.springframework.beans.factory.annotation.Autowired; // Used for automatic dependency injection.
import org.springframework.stereotype.Controller; // Marks this class as a Spring MVC controller.
import org.springframework.ui.Model; // Used to pass data from the controller to the view (Thymeleaf template).
import org.springframework.web.bind.annotation.*; // Imports various Spring MVC annotation (e.g., @GetMapping, @PostMapping, @PathVariable).

@Controller // This annotation indicates that this class is a Spring MVC Controller, handling incoming web requests.
@RequestMapping("/products") // All methods in this controller will handle requests that start with `/products`.
public class ProductController {

    @Autowired // Automatically injects an instance of ProductService into this controller. Spring finds a bean of type ProductService and provides it here.
    private ProductService productService; // Declares a field for ProductService.

    @GetMapping // Handles GET requests to `/products` (due to @RequestMapping("/products") at class level).
    public String listProducts(Model model) { // Method to display the list of products. Takes a Model object to pass data to the view.
        model.addAttribute("products", productService.getAllProducts()); // Adds a list of all products (retrieved from productService) to the model under the name "products". This list will be accessible in the Thymeleaf template.
        return "products"; // Returns the name of the Thymeleaf template (`products.html`) to be rendered.
    }

    @GetMapping("/new") // Handles GET requests to `/products/new`.
    public String showAddProductForm(Model model) { // Method to display the form for adding a new product.
        model.addAttribute("product", new Product()); // Adds a new, empty Product object to the model. This is used to bind form fields to a new product instance.
        return "add-edit-product"; // Returns the `add-edit-product.html` template.
    }

    @PostMapping("/save") // Handles POST requests to `/products/save`. This is used for both adding new products and updating existing ones.
    public String saveProduct(@ModelAttribute("product") Product product) { // Binds incoming form data to a Product object. `@ModelAttribute` fetches the Product object from the model (or creates a new one if not present) and populates its fields from request parameters.
        productService.saveProduct(product); // Calls the service layer to save (or update) the product in the database.
        return "redirect:/products"; // Redirects the user to the `/products` URL, which will trigger the `listProducts` method, showing the updated list.
    }

    @GetMapping("/edit/{id}") // Handles GET requests to `/products/edit/{id}`, where `{id}` is a path variable.
    public String showEditProductForm(@PathVariable Long id, Model model) { // Takes the product ID from the URL path and a Model object.
        Product product = productService.getProductById(id); // Retrieves the product by its ID from the service.
        if (product != null) { // Checks if the product was found.
            model.addAttribute("product", product); // Adds the retrieved product to the model for pre-filling the edit form.
            return "add-edit-product"; // Returns the `add-edit-product.html` template.
        }
        return "redirect:/products"; // If product not found, redirects back to the product list.
    }

    @GetMapping("/delete/{id}") // Handles GET requests to `/products/delete/{id}`.
    public String deleteProduct(@PathVariable Long id) { // Takes the product ID from the URL path.
        productService.deleteProduct(id); // Calls the service layer to delete the product by ID.
        return "redirect:/products"; // Redirects back to the product list after deletion.
    }
}