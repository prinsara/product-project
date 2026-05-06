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

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final AdminRepository adminRepository;

    @Transactional
    public GetProductResponse createProduct(GetProductRequest request) {

        // 1. adminId로 관리자 조회
        //상품 여러 개 : 관리자 1명
        Admin admin = findAdminById(request.getAdminId());

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

    @Transactional(readOnly = true)
    public List<GetProductResponse> getProducts() {

        // 1. 전체 상품 조회
        List<Product> products = productRepository.findAll();

        // 2. Entity 리스트를 응답 DTO 리스트로 변환 후 반환
        return products.stream()
                .map(product -> new GetProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getPrice(),
                        product.getAdmin().getName()
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    public GetProductResponse getProduct(Long productId) {

        // 1. 상품 id로 단건 조회
        Product product = findProductById(productId);

        // 2. 조회한 상품을 응답 DTO로 변환 후 반환
        return new GetProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getAdmin().getName()
        );
    }

    @Transactional
    public GetProductResponse updateProduct(Long productId, GetProductRequest request) {

        // 1. 수정할 상품 조회
        Product product = findProductById(productId);

        // 2. adminId로 관리자 조회
        Admin admin = findAdminById(request.getAdminId());

        // 3. 상품 정보 수정
        product.update(
                request.getName(),
                request.getPrice(),
                admin
        );

        // 4. 수정된 상품을 응답 DTO로 변환 후 반환
        return new GetProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getAdmin().getName()
        );
    }

    @Transactional
    public void deleteProduct(Long productId) {

        // 1. 삭제할 상품 조회
        Product product = findProductById(productId);

        // 2. 상품 삭제
        productRepository.delete(product);
    }

    private Product findProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
    }

    private Admin findAdminById(Long adminId) {
        return adminRepository.findById(adminId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 관리자입니다."));
    }
}
