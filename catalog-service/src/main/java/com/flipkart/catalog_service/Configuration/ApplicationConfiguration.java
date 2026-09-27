package com.flipkart.catalog_service.Configuration;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "catalog")
@Component
@Getter
@Setter
public class ApplicationConfiguration {
    private int pageNo=10;
}
