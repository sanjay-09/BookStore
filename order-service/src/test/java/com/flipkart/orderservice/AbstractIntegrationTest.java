package com.flipkart.orderservice;


import com.github.tomakehurst.wiremock.client.WireMock;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.wiremock.integrations.testcontainers.WireMockContainer;

import java.math.BigDecimal;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestcontainersConfiguration.class)
public abstract class AbstractIntegrationTest {
    @LocalServerPort
    int port;


    @Container
    static WireMockContainer wireMock =
            new WireMockContainer("wiremock/wiremock:3.10.0");

    @BeforeEach
    void setup(){
        RestAssured.port=port;
    }


    @BeforeAll
    static void beforeAll(){
        wireMock.start();
        configureFor(wireMock.getHost(),wireMock.getPort());
    }
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry){
        registry.add("orders.catalog-service-url",wireMock::getBaseUrl);
    }
    protected static void mockGetProductByCode(
            String code,
            String name,
            BigDecimal price
    ) {
        stubFor(
                WireMock.get(
                                urlMatching("/api/v1/products/" + code)
                        )
                        .willReturn(
                                aResponse()
                                        .withHeader(
                                                "Content-Type",
                                                MediaType.APPLICATION_JSON_VALUE
                                        )
                                        .withStatus(200)
                                        .withBody(
                                                """
                                                {
                                                    "code": "%s",
                                                    "name": "%s",
                                                    "price": %f
                                                }
                                                """.formatted(
                                                        code,
                                                        name,
                                                        price.doubleValue()
                                                )
                                        )
                        )
        );
    }
}
