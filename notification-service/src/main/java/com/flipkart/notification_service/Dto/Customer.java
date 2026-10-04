package com.flipkart.notification_service.Dto;


import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Customer {
    @NotBlank(message="Customer name is required")
    private String name;
    @NotBlank(message = "email name is required")
    private String email;
    @NotBlank(message="phone name is required")
    private String phone;
}
