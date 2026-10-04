package com.flipkart.orderservice.Dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ProductResDto {
    private String code;
    private String name;
    String imageUrl;
    double price;
}
