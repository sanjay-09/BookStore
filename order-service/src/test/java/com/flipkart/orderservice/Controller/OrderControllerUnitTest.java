package com.flipkart.orderservice.Controller;

import com.flipkart.orderservice.Service.SecurityService;
import tools.jackson.databind.ObjectMapper;
import com.flipkart.orderservice.Dto.CreateOrderReqDto;
import com.flipkart.orderservice.Dto.CreateOrderResDto;
import com.flipkart.orderservice.Service.OrderService;
import org.springframework.http.MediaType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static com.flipkart.orderservice.Test.TestDataFactory.*;
import static org.junit.jupiter.api.Named.named;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
public class OrderControllerUnitTest  {

    @MockitoBean
    private OrderService orderService;

    @MockitoBean
    private SecurityService securityService;


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @ParameterizedTest(name="[{index}]-{0}")
    @MethodSource("createOrderRequestProvider")
    void shouldReturnBadRequestWhenOrderPayloadIsInvalid(CreateOrderReqDto createOrderReqDto) throws Exception {
        given(orderService.createOrder(eq("sanjay") ,any(CreateOrderReqDto.class))).willReturn(null);
        mockMvc.perform(post("/api/v1/order").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createOrderReqDto))).
                andExpect(status().isBadRequest());

    }

    static Stream<Arguments> createOrderRequestProvider() {
        return Stream.of(
                arguments(named("Order with Invalid Customer", createOrderRequestWithInvalidCustomer())),
                arguments(named("Order with Invalid Delivery Address", createOrderRequestWithInvalidDeliveryAddress())),
                arguments(named("Order with No Items", createOrderRequestWithNoItems())));
    }




}
