package com.example.productmanagementproject.repository;

import com.example.productmanagementproject.entity.Admin;
import org.hibernate.boot.models.JpaAnnotations;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
