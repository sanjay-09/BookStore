package com.flipkart.orderservice.Dto;


import com.flipkart.orderservice.Model.Helper.Address;
import com.flipkart.orderservice.Model.Helper.Customer;
import com.flipkart.orderservice.Model.OrderItem;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent {
    private String eventId;
    private String orderNumber;
    private Set<OrderItemEvent> items;
    private Customer customer;
    private Address address;
    private LocalDateTime createdAt;

}
