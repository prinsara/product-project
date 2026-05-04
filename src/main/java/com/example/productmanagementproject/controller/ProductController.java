package com.example.productmanagementproject.controller;

import com.example.productmanagementproject.dto.GetProductRequest;
import com.example.productmanagementproject.dto.GetProductResponse;
import com.example.productmanagementproject.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor //final 붙은 필드 생성자 자동으로 만들고 의존성 주입 쉽게 하게 해주는 것
public class ProductController {

    private final ProductService productService;

    //상품 생성
    @PostMapping
    public ResponseEntity<GetProductResponse> createProduct(@RequestBody GetProductRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(request));
    }
}
