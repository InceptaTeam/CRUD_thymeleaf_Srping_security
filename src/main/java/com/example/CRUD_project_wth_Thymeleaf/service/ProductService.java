package com.example.CRUD_project_wth_Thymeleaf.service; // Package declaration

import com.example.CRUD_project_wth_Thymeleaf.model.Product; // Imports the Product entity.
import com.example.CRUD_project_wth_Thymeleaf.repository.ProductRepository; // Imports the ProductRepository.
import org.springframework.beans.factory.annotation.Autowired; // Used for automatic dependency injection.
import org.springframework.stereotype.Service; // Marks this class as a Spring service component.

import java.util.List; // Imports List interface.

@Service // Indicates that this class is a "Service" component in the Spring application context. Services typically encapsulate the business logic of an application.
public class ProductService {

    @Autowired // Automatically injects an instance of ProductRepository into this service.
    private ProductRepository productRepository; // Declares a field for ProductRepository.

    public List<Product> getAllProducts() { // Method to retrieve all products.
        return productRepository.findAll(); // Calls the repository to get all products from the database and returns them as a List.
    }

    public Product getProductById(Long id) { // Method to retrieve a product by its ID.
        // findById returns an Optional<Product>, so .orElse(null) returns the Product object or null if not found.
        return productRepository.findById(id).orElse(null); // Calls the repository to find a product by ID.
    }

    public void saveProduct(Product product) { // Method to save or update a product.
        productRepository.save(product); // Calls the repository to persist the product (if new) or update it (if existing).
    }

    public void deleteProduct(Long id) { // Method to delete a product by its ID.
        productRepository.deleteById(id); // Calls the repository to delete the product with the given ID from the database.
    }
}