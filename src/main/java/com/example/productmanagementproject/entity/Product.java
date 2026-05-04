package com.example.productmanagementproject.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "products")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //상품 이름
    private String name;

    //상품 가격
    private Long price;

    //관리자
    /**
     * 여러 product가 하나의 admin에 속함 -> 상품 여러 개 : 관리자 한 명 <br>
     * fetch = FetchType.LAZY -> 상품 조회할 때 관리자 정보까지 바로 가져오기X getAdmin() 사용할 때 가져오기 <br>
     * products 테이블 외래키 컬럼 이름을 admin_id로 쓰겠다 -> 상품 테이블이 관리자 PK를 들고 있는 상태가 된다.
     *
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    public Product(Admin admin, Long price, String name) {
        this.admin = admin;
        this.price = price;
        this.name = name;
    }
}
