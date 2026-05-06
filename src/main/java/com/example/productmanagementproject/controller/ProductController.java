package com.example.productmanagementproject.controller;

import com.example.productmanagementproject.dto.GetProductRequest;
import com.example.productmanagementproject.dto.GetProductResponse;
import com.example.productmanagementproject.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    //상품 전체 조회
    @GetMapping
    public ResponseEntity<List<GetProductResponse>> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    //상품 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<GetProductResponse> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }

    //상품 수정
    @PutMapping("/{id}")
    public ResponseEntity<GetProductResponse> updateProduct(
            @PathVariable Long id,
            @RequestBody GetProductRequest request
    ) {
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }

    //상품 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
