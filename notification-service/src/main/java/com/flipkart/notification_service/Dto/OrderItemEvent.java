package com.flipkart.notification_service.Dto;


import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemEvent {
    private String code;
    private String name;
    private BigDecimal price;
    private Integer quantity;
}
