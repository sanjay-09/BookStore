package com.flipkart.orderservice.Controller;

import com.flipkart.orderservice.AbstractIntegrationTest;
import com.flipkart.orderservice.Dto.OrderInfo;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.List;

import static io.restassured.RestAssured.given;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;


@Sql("/test-orders.sql")
public class OrderControllerTest extends AbstractIntegrationTest {

    @Test
    void shouldCreateTheOrder(){
        mockGetProductByCode("P100","The Hunger Games",new BigDecimal("34.0"));
        String payload= """
                {
                  "orderItemsReq": [
                    {
                      "code": "P100",
                      "name": "The Hunger Games",
                      "price": 34.0,
                      "quantity": 1
                    }
                  ],
                  "customer": {
                    "name": "Sanjay Singh",
                    "email": "sanjay@example.com",
                    "phone": "9876543210"
                  },
                  "address": {
                    "addressLine1": "123 Main Street",
                    "addressLine2": "Near City Mall",
                    "city": "Noida",
                    "state": "Uttar Pradesh",
                    "zipCode": "201301",
                    "country": "India"
                  }
                }
                """;

        given().contentType(ContentType.JSON).body(payload).when().post("/api/v1/order").then().statusCode(HttpStatus.CREATED.value()).body("orderId",notNullValue());

    }

    @Test
    void shouldNotCreateTheOrder(){
        String payload= """
                {
                  "orderItemsReq": [
                    {
                      "code": "IPHONE-15",
                      "name": "iPhone 15",
                      "price": 69999.00,
                      "quantity": 1
                    },
                    {
                      "code": "AIRPODS-PRO",
                      "name": "AirPods Pro",
                      "price": 24999.00,
                      "quantity": 2
                    }
                  ],
                  "customer": {
                    "email": "sanjay@example.com",
                    "phone": "9876543210"
                  },
                  "address": {
                    "addressLine1": "123 Main Street",
                    "addressLine2": "Near City Mall",
                    "city": "Noida",
                    "state": "Uttar Pradesh",
                    "zipCode": "201301",
                    "country": "India"
                  }
                }
                """;
        given()
                .contentType(ContentType.JSON)
                .body(payload).when().post("/api/v1/order").then().statusCode(HttpStatus.BAD_REQUEST.value()).body("detail",equalTo("One or more fields have validation errors"));

    }

    @Test
    void getAllOrders(){
      List<OrderInfo> orderInfo=given().contentType(ContentType.JSON).when().get("/api/v1/order").then().statusCode(HttpStatus.OK.value()).extract().body().as(new TypeRef<>()
        {});

      assertThat(orderInfo).hasSize(2);


    }
    @Test
    void getOrderbyOrderNumber(){
        String orderNumber="order-123";
        given().contentType(ContentType.JSON).when().get("/api/v1/order/{orderNumber}",orderNumber).then().statusCode(200).body("orderNumber",is(orderNumber));
    }
}
