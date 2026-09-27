package com.flipkart.catalog_service.Mapper;

import com.flipkart.catalog_service.Dto.ProductResDto;
import com.flipkart.catalog_service.Model.Product;

public class ProductToDto {
    public static ProductResDto toDto(Product product){

        return ProductResDto
                .builder().name(product.getName()).code(product.getCode()).price(product.getPrice().doubleValue()).imageUrl(product.getImageUrl()).build();

    }
}
