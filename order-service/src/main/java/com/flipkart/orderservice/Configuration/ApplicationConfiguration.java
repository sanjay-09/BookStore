package com.flipkart.orderservice.Configuration;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "orders")
@Getter
@Setter
public class ApplicationConfiguration {
    private String orderEventsExchange;
    private String newOrdersQueue;
    private String deliveredOrdersQueue;
    private String cancelledOrdersQueue;
    private String errorsOrdersQueue;
    private String catalogServiceUrl;
}
