package com.flipkart.orderservice.Service;

import com.flipkart.orderservice.Client.Catalog.ProductServiceClient;
import com.flipkart.orderservice.Dto.CreateOrderReqDto;
import com.flipkart.orderservice.Dto.OrderItemReqDto;
import com.flipkart.orderservice.Dto.ProductResDto;
import com.flipkart.orderservice.Exception.InvalidOrderException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderValidator {

    private final ProductServiceClient productServiceClient;

    public void validate(CreateOrderReqDto createOrderReqDto){
        Set<OrderItemReqDto> items=createOrderReqDto.getOrderItemsReq();
        for(OrderItemReqDto item:items){
            ProductResDto product=productServiceClient
                    .getProductsByCode(item.getCode()).orElseThrow(()->new InvalidOrderException("Invalid Product code"+item.getCode()));
            if(item.getPrice().compareTo(BigDecimal.valueOf(product.getPrice()))!=0){
                log.error("Product Price not matching. Actual price:{},received price:{}",product.getPrice(),item.getPrice());
                throw new InvalidOrderException("Product price not matching");

            }
        }


    }


}
