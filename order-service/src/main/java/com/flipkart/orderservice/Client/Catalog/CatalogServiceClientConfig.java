package com.flipkart.orderservice.Client.Catalog;


import com.flipkart.orderservice.Configuration.ApplicationConfiguration;
import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.http.client.HttpClientSettings;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Configuration
public class CatalogServiceClientConfig {

    @Bean
    RestClient restClient(ApplicationConfiguration applicationConfiguration){
        HttpClientSettings settings = HttpClientSettings.defaults()
                .withConnectTimeout(Duration.ofSeconds(5))
                .withReadTimeout(Duration.ofSeconds(5));

        return RestClient.builder()
                .baseUrl(applicationConfiguration.getCatalogServiceUrl())
                .requestFactory(
                        ClientHttpRequestFactoryBuilder.detect()
                                .build(settings)
                )
                .build();
    }
}
