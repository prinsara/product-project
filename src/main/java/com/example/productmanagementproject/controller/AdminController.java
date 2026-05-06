package com.example.productmanagementproject.controller;

import com.example.productmanagementproject.dto.AdminSignUpRequest;
import com.example.productmanagementproject.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admins")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@RequestBody AdminSignUpRequest request) {
        adminService.signUp(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
