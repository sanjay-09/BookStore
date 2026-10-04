package com.flipkart.orderservice.Test;

import com.flipkart.orderservice.Dto.CreateOrderReqDto;
import com.flipkart.orderservice.Dto.CreateOrderResDto;
import com.flipkart.orderservice.Dto.OrderItemReqDto;
import com.flipkart.orderservice.Model.Helper.Address;
import com.flipkart.orderservice.Model.Helper.Customer;
import org.instancio.Instancio;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.instancio.Select.field;

public class TestDataFactory {

    static final Set<OrderItemReqDto> VALID_ORDER_ITEMS=Set.of(new OrderItemReqDto("P100","Product 1",new BigDecimal("25.50"),1));

    static final List<String> VALID_COUNTRIES=List.of("India","Germany");



    public static CreateOrderReqDto createValidOrderRequest(){
        return Instancio.of(CreateOrderReqDto.class).generate(field(Customer::getEmail),gen->gen.text().pattern("#a#a#a#a#a#a@mail.com"))
                .set(field(CreateOrderReqDto::getOrderItemsReq),VALID_ORDER_ITEMS).generate(field(Address::getCountry),gen->gen.oneOf(VALID_COUNTRIES))
                .create();
    }

    public static CreateOrderReqDto createOrderRequestWithInvalidCustomer(){
        return Instancio.of(CreateOrderReqDto.class).generate(field(Customer::getEmail),gen->gen.text().pattern("#a#a#a#a#a#a@mail.com"))
                .set(field(Customer::getPhone),"")
                .generate(field(Address::getCountry),gen->gen.oneOf(VALID_COUNTRIES))
                .set(field(CreateOrderReqDto::getOrderItemsReq),VALID_ORDER_ITEMS).create();
    }

    public static CreateOrderReqDto createOrderRequestWithInvalidDeliveryAddress(){
        return Instancio.of(CreateOrderReqDto.class).generate(field(Customer::getEmail),gen->gen.text().pattern("#a#a#a#a#a#a@mail.com"))
                .set(field(Address::getCountry),"")
                .set(field(CreateOrderReqDto::getOrderItemsReq),VALID_ORDER_ITEMS).create();
    }
    public static CreateOrderReqDto createOrderRequestWithNoItems(){
        return Instancio.of(CreateOrderReqDto.class).generate(field(Customer::getEmail),gen->gen.text().pattern("#a#a#a#a#a#a@mail.com"))
                .set(field(Customer::getPhone),"")
                .set(field(Address::getCountry),"")
                .set(field(CreateOrderReqDto::getOrderItemsReq),Set.of()).create();
    }
}
