package com.flipkart.orderservice.Dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class OrderItemReqDto {
    @NotBlank(message = "Code is required")
    private String code;
    @NotBlank(message="Name is required")
    private String name;
    @NotNull(message = "price is required")
    private BigDecimal price;
    @NotNull @Min(1)
    private Integer quantity;
}
