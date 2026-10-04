package com.flipkart.orderservice.Controller;


import com.flipkart.orderservice.Dto.CreateOrderReqDto;
import com.flipkart.orderservice.Dto.CreateOrderResDto;
import com.flipkart.orderservice.Dto.OrderInfo;
import com.flipkart.orderservice.Dto.OrderInfoRes;
import com.flipkart.orderservice.Service.OrderService;
import com.flipkart.orderservice.Service.SecurityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final SecurityService securityService;

    @PostMapping
    public ResponseEntity<?> createOrder(@Valid @RequestBody CreateOrderReqDto createOrderReqDto){
        log.info("Request registered");
        CreateOrderResDto res=this.orderService.createOrder("sanjay",createOrderReqDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);

    }

    @GetMapping
    public ResponseEntity<?> getAllOrders(){
        String userName=this.securityService.getLoginUserName();
        log.info("Fetching orders for user:{}",userName);
        List<OrderInfo> ordersInfo=this.orderService.findOrders(userName);
        return ResponseEntity.status(HttpStatus.OK).body(ordersInfo);

    }
    @GetMapping("/{orderNumber}")
    public ResponseEntity<?> getOrderByNumber(@PathVariable String orderNumber){
        String userName=this.securityService.getLoginUserName();
        OrderInfoRes orderInfoRes=this.orderService.findByUserOrder(userName,orderNumber);
        return ResponseEntity.status(HttpStatus.OK).body(orderInfoRes);

    }
}
