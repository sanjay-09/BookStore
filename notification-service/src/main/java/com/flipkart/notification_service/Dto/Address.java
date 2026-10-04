package com.flipkart.notification_service.Dto;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Address {
    @NotBlank(message = "addressLine1 is required")
    private String addressLine1;
    private String addressLine2;
    @NotBlank(message =  "city name is required")
    private String city;
    @NotBlank(message="state name is required")
    private String state;
    @NotBlank(message="zip code is required")
    private String zipCode;
    @NotBlank(message="country code is required")
    private String country;
}