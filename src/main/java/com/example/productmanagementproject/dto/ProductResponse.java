package com.example.productmanagementproject.dto;

import lombok.Getter;

@Getter
public class ProductResponse {

    private final Long id;
    private final String name;
    private final Long price;
    private final String adminName;

    public ProductResponse(Long id, String name, Long price, String adminName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.adminName = adminName;
    }
}
