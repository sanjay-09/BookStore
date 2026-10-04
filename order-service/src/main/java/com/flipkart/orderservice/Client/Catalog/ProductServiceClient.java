package com.flipkart.orderservice.Client.Catalog;

import com.flipkart.orderservice.Dto.ProductResDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductServiceClient {
    private final RestClient restClient;

    @CircuitBreaker(name="catalog-service")
    @Retry(name="catalog-service",fallbackMethod = "getProductByCodeFallback")
    public Optional<ProductResDto> getProductsByCode(String code){

            ProductResDto productResDto=restClient.get().uri("/api/v1/products/{code}",code)
                    .retrieve().body(ProductResDto.class);
            return Optional.ofNullable(productResDto);


    }

    Optional<ProductResDto> getProductByCodeFallback(String code,Throwable t){
        System.out.println("ProductServiceClient.getProductByCodeFallback:code: "+code);
        return Optional.empty();

    }
}
