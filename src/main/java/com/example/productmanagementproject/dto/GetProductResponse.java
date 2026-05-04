package com.example.productmanagementproject.dto;

import lombok.Getter;

@Getter
public class GetProductResponse {

    private final Long id;
    private final String name;
    private final Long price;
    private final String adminName;

    public GetProductResponse(Long id, String name, Long price, String adminName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.adminName = adminName;
    }
}
