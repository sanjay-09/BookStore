package com.flipkart.orderservice.Dto;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfo {
    private String orderNumber;
    private String status;
}
