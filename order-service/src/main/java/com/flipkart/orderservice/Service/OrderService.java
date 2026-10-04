package com.flipkart.orderservice.Service;


import com.flipkart.orderservice.Dto.*;
import com.flipkart.orderservice.Exception.InvalidOrderException;
import com.flipkart.orderservice.Mapper.OrderMapper;
import com.flipkart.orderservice.Model.Helper.Enum.OrderEventType;
import com.flipkart.orderservice.Model.Helper.Enum.OrderStatus;
import com.flipkart.orderservice.Model.Order;
import com.flipkart.orderservice.Model.OrderItem;
import com.flipkart.orderservice.Repository.OrderEventRepository;
import com.flipkart.orderservice.Repository.OrderRepository;
import com.flipkart.orderservice.Repository.Projection.OrderSummary;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderValidator orderValidator;
    private final OrderEventService orderEventService;
    private static final List<String> DELIVERY_ALLOWED_COUNTRIES = List.of("INDIA", "USA", "GERMANY", "UK");



    public CreateOrderResDto createOrder(String username, CreateOrderReqDto createOrderReqDto){

        orderValidator.validate(createOrderReqDto);

        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .customer(createOrderReqDto.getCustomer())
                .deliveryAddress(createOrderReqDto.getAddress())
                .updatedAt(LocalDateTime.now())
                .userName(username)
                .status(OrderStatus.NEW)
                .createdAt(LocalDateTime.now())
                .build();

        Set<OrderItem> orderItems = createOrderReqDto.getOrderItemsReq()
                .stream()
                .map(item -> OrderItem.builder()
                        .code(item.getCode())
                        .name(item.getName())
                        .price(item.getPrice())
                        .quantity(item.getQuantity())
                        .order(order)          // important
                        .build())
                .collect(Collectors.toSet());

        order.setItems(orderItems);

        Order savedOrder=this.orderRepository.save(order);


        OrderCreatedEvent createEvent= OrderMapper.toOrderCreateEvent(order);
        this.orderEventService.save(createEvent, OrderEventType.ORDER_CREATED);


        return CreateOrderResDto.builder().orderId(savedOrder.getId()).build();


    }

    public void processNewOrders(){
        List<Order> orders=this.orderRepository.findByStatus(OrderStatus.NEW);
        log.info("Found {} new orders to process",orders.size());
        for(Order order:orders){
            this.process(order);

        }

    }

    private void process(Order order){
        try{
            if(canBeDelivered(order)){
                log.info("OrderNumber-{} can be delivered",order.getOrderNumber());
                order.setStatus(OrderStatus.DELIVERED);
                OrderCreatedEvent createEvent= OrderMapper.toOrderCreateEvent(order);
                this.orderEventService.save(createEvent,OrderEventType.ORDER_DELIVERED);

            }
            else{
                log.info("OrderNumber-{} can not be deliverd",order.getOrderNumber());
                order.setStatus(OrderStatus.CANCELLED);
                OrderCreatedEvent createEvent= OrderMapper.toOrderCreateEvent(order);
                this.orderEventService.save(createEvent,OrderEventType.ORDER_CANCELLED);
            }

        }
        catch (RuntimeException e){
            log.error("failed to process the order with orderNumber-{}",order.getOrderNumber(),e);
            order.setStatus(OrderStatus.ERROR);
            OrderCreatedEvent createEvent= OrderMapper.toOrderCreateEvent(order);
            this.orderEventService.save(createEvent,OrderEventType.ORDER_PROCESSING_FAILED);

        }

    }


    private boolean canBeDelivered(Order order) {
        return DELIVERY_ALLOWED_COUNTRIES.contains(
                order.getDeliveryAddress().getCountry().toUpperCase());
    }




    public List<OrderInfo> findOrders(String userName){
        List<OrderSummary> orders=this.orderRepository.findByUserName(userName);
        return orders.stream().map(order->OrderInfo.builder().orderNumber(order.getOrderNumber()).status(order.getStatus().name()).build()).toList();
    }

    public OrderInfoRes findByUserOrder(String userName,String orderNumber){
        Order order=this.orderRepository.findByUserNameAndOrderNumber(userName,orderNumber).orElseThrow(()->new InvalidOrderException("order with orderNumber does not exist "+orderNumber));
        return OrderMapper.toOrderInfoRes(order);


    }








}
