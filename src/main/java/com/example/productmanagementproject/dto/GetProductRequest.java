package com.example.productmanagementproject.dto;

import lombok.Getter;

@Getter
public class GetProductRequest {

    private String name;
    private Long price;
    private Long adminId;
}
