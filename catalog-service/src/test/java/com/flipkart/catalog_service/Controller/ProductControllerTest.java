package com.flipkart.catalog_service.Controller;

import com.flipkart.catalog_service.AbstractIntegrationTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;


@Sql("/test-data.sql")
public class ProductControllerTest extends AbstractIntegrationTest {

    @Test
    void shouldReturnProducts(){
        given().contentType(ContentType.JSON).
                when().get("/api/v1/products")
                .then()
                .statusCode(200)
                .body("content",hasSize(10));

    }

    @Test
    void shouldReturnProductByCode(){
        given().when().get("/api/v1/products/P102").then().statusCode(200)
                .body("code",equalTo("P102"))
                .body("name",equalTo("The Chronicles of Narnia"));
    }

    @Test
    void shouldNotReturnProductByCode(){
        given().when().get("/api/v1/products/P10").then().statusCode(404);
    }
}
