package com.example.productmanagementproject.repository;

import com.example.productmanagementproject.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
