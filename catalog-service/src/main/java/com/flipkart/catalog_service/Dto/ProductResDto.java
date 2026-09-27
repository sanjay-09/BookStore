package com.flipkart.catalog_service.Dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class ProductResDto {
    private String code;
    private String name;
    String imageUrl;
    double price;
}
