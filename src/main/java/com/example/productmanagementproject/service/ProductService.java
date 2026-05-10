package com.example.productmanagementproject.service;

import com.example.productmanagementproject.dto.ProductRequest;
import com.example.productmanagementproject.dto.ProductResponse;
import com.example.productmanagementproject.entity.Admin;
import com.example.productmanagementproject.entity.Product;
import com.example.productmanagementproject.repository.AdminRepository;
import com.example.productmanagementproject.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final AdminRepository adminRepository;

    @Transactional
    public ProductResponse createProduct(ProductRequest request, Long loginAdminId) {

        // 1. adminId로 관리자 조회
        //상품 여러 개 : 관리자 1명
        Admin admin = findAdminById(loginAdminId);

        // 2. 요청 DTO를 Entity로 변환
        Product product = new Product(
                request.getName(),
                request.getPrice(),
                admin
        );

        // 3. 상품 저장
        Product savedProduct = productRepository.save(product);

        // 4. 저장된 데이터를 응답 DTO로 변환 후 반환
        return new ProductResponse(
                savedProduct.getId(),
                savedProduct.getName(),
                savedProduct.getPrice(),
                savedProduct.getAdmin().getName()
        );
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getProducts() {

        // 1. 전체 상품 조회
        List<Product> products = productRepository.findAll();

        // 2. Entity 리스트를 응답 DTO 리스트로 변환 후 반환
        return products.stream()
                .map(product -> new ProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getPrice(),
                        product.getAdmin().getName()
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductResponse getProduct(Long productId) {

        // 1. 상품 id로 단건 조회
        Product product = findProductById(productId);

        // 2. 조회한 상품을 응답 DTO로 변환 후 반환
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getAdmin().getName()
        );
    }

    @Transactional
    public ProductResponse updateProduct(Long productId, ProductRequest request, Long loginAdminId) {

        // 1. 수정할 상품 조회
        Product product = findProductById(productId);

        // 2. 로그인한 관리자가 본인 상품인지 확인
        validateOwner(product, loginAdminId);

        // 3. 상품 정보 수정
        product.update(
                request.getName(),
                request.getPrice(),
                product.getAdmin()
        );

        // 4. 수정된 상품을 응답 DTO로 변환 후 반환
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getAdmin().getName()
        );
    }

    @Transactional
    public void deleteProduct(Long productId, Long loginAdminId) {

        // 1. 삭제할 상품 조회
        Product product = findProductById(productId);

        // 2. 로그인한 관리자가 본인 상품인지 확인
        validateOwner(product, loginAdminId);

        // 3. 상품 삭제
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

    public Long getLoginAdminId(Long loginAdminId) {
        if (loginAdminId == null) {
            throw new ResponseStatusException(UNAUTHORIZED, "로그인이 필요합니다.");
        }
        return loginAdminId;
    }

    private void validateOwner(Product product, Long loginAdminId) {
        if (!product.getAdmin().getId().equals(loginAdminId)) {
            throw new ResponseStatusException(FORBIDDEN, "본인 상품만 수정 또는 삭제할 수 있습니다.");
        }
    }
}
