package com.example.productmanagementproject.controller;

import com.example.productmanagementproject.dto.AdminLoginRequest;
import com.example.productmanagementproject.dto.AdminSignUpRequest;
import com.example.productmanagementproject.service.AdminService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admins")
@RequiredArgsConstructor
public class AdminController {

    private static final String LOGIN_ADMIN_ID = "LOGIN_ADMIN_ID";

    private final AdminService adminService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@RequestBody AdminSignUpRequest request) {
        adminService.signUp(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody AdminLoginRequest request, HttpSession session) {
        Long adminId = adminService.login(request);
        session.setAttribute(LOGIN_ADMIN_ID, adminId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok().build();
    }
}
