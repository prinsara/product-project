package com.example.productmanagementproject.service;

import com.example.productmanagementproject.dto.GetProductRequest;
import com.example.productmanagementproject.dto.GetProductResponse;
import com.example.productmanagementproject.entity.Admin;
import com.example.productmanagementproject.entity.Product;
import com.example.productmanagementproject.repository.AdminRepository;
import com.example.productmanagementproject.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final AdminRepository adminRepository;

    @Transactional
    public GetProductResponse createProduct(GetProductRequest request) {

        // 1. adminId로 관리자 조회
        //상품 여러 개 : 관리자 1명
        Admin admin = adminRepository.findById(request.getAdminId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 관리자입니다."));

        // 2. 요청 DTO를 Entity로 변환
        Product product = new Product(
                request.getName(),
                request.getPrice(),
                admin
        );

        // 3. 상품 저장
        Product savedProduct = productRepository.save(product);

        // 4. 저장된 데이터를 응답 DTO로 변환 후 반환
        return new GetProductResponse(
                savedProduct.getId(),
                savedProduct.getName(),
                savedProduct.getPrice(),
                savedProduct.getAdmin().getName()
        );
    }

}
