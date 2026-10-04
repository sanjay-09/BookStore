package com.flipkart.orderservice.Dto;


import com.flipkart.orderservice.Model.Helper.Address;
import com.flipkart.orderservice.Model.Helper.Customer;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfoRes {
    private String orderNumber;
    private String userName;
    private Set<OrderItemEvent> items;
    private Customer customer;
    private Address address;
    private LocalDateTime createdAt;
    private String comments;
    private BigDecimal totalAmount;
}
