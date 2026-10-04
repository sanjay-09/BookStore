package com.flipkart.orderservice.Dto;

import com.flipkart.orderservice.Model.Helper.Address;
import com.flipkart.orderservice.Model.Helper.Customer;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CreateOrderReqDto {

    @NotNull
    @NotEmpty
    private Set<OrderItemReqDto> orderItemsReq;

    @Valid
    @NotNull(message = "Customer is required")
    private Customer customer;

    @Valid
    @NotNull(message = "address is required")
    private Address address;


}
