package com.example.CRUD_project_wth_Thymeleaf.model; // Package declaration

import jakarta.persistence.Entity; // Annotation to mark this class as a JPA entity.
import jakarta.persistence.GeneratedValue; // Annotation for automatic ID generation.
import jakarta.persistence.GenerationType; // Strategy for ID generation.
import jakarta.persistence.Id; // Marks the primary key field.

@Entity // Declares this class as a JPA entity. This means it maps to a table in the database. By default, the table name will be "Product".
public class Product {

    @Id // Marks this field as the primary key of the entity.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configures the primary key to be automatically generated by the database (e.g., auto-increment in MySQL/H2).
    private Long id; // Unique identifier for the product.

    private String name; // Name of the product.
    private String description; // Description of the product.
    private double price; // Price of the product.

    // Getters and Setters (omitted for brevity in explanation, but essential for JPA and Thymeleaf to access properties)
    // These methods allow Spring and JPA to read from and write to the fields of a Product object.
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}