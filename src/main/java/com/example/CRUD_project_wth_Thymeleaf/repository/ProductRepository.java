package com.example.CRUD_project_wth_Thymeleaf.repository; // Package declaration

import com.example.CRUD_project_wth_Thymeleaf.model.Product; // Imports the Product entity.
import org.springframework.data.jpa.repository.JpaRepository; // Imports JpaRepository.
import org.springframework.stereotype.Repository; // Marks this interface as a Spring Data repository.

@Repository // Indicates that this interface is a "repository", which is a mechanism for encapsulating storage, retrieval, and search behavior which emulates a collection of objects. Spring will automatically create an implementation for this interface at runtime.
public interface ProductRepository extends JpaRepository<Product, Long> {
    // By extending JpaRepository, you automatically inherit standard CRUD (Create, Read, Update, Delete) operations
    // for the Product entity with a primary key of type Long.
    // Examples of inherited methods: save(), findById(), findAll(), deleteById(), etc.
    // No method implementations are needed here; Spring Data JPA handles it.
}