package com.flipkart.orderservice.Mapper;

import com.flipkart.orderservice.Dto.OrderCreatedEvent;
import com.flipkart.orderservice.Dto.OrderInfo;
import com.flipkart.orderservice.Dto.OrderInfoRes;
import com.flipkart.orderservice.Dto.OrderItemEvent;
import com.flipkart.orderservice.Model.Order;
import com.flipkart.orderservice.Model.OrderItem;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
public class OrderMapper {

    public static OrderCreatedEvent toOrderCreateEvent(Order order){

        Set<OrderItemEvent> events=getOrderItemEvents(order);
        return OrderCreatedEvent.builder().eventId(UUID.randomUUID().toString())
                .orderNumber(order.getOrderNumber()).items(events).createdAt(order.getCreatedAt()).address(order.getDeliveryAddress())
                .customer(order.getCustomer()).build();

    }

    public static OrderInfo toOrderInfo(Order order){
        return OrderInfo.builder().orderNumber(order.getOrderNumber()).status(order.getStatus().name()).build();

    }

    public static OrderInfoRes toOrderInfoRes(Order order){
        Set<OrderItemEvent> orderItemEvents=getOrderItemEvents(order);

        BigDecimal totalAmount=new BigDecimal(0);
        for(OrderItemEvent event:orderItemEvents){
            totalAmount = totalAmount.add(event.getPrice());

        }

        return OrderInfoRes.builder().orderNumber(order.getOrderNumber()).items(orderItemEvents).totalAmount(totalAmount)
                .address(order.getDeliveryAddress()).customer(order.getCustomer()).comments(order.getComments()).userName(order.getUserName()).build();



    }




    public static Set<OrderItemEvent> getOrderItemEvents(Order order){
        Set<OrderItemEvent> events=order.getItems().stream()
                .map(item->OrderItemEvent.builder().name(item.getName()).price(item.getPrice()).quantity(item.getQuantity()).code(item.getCode()).build()).collect(Collectors.toSet());
        return events;


    }
}
