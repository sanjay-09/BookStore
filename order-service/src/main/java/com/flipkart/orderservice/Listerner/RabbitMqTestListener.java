package com.flipkart.orderservice.Listerner;

import com.flipkart.orderservice.Dto.Payload;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqTestListener {

//    @RabbitListener(queues = "${orders.new-orders-queue}")
    public void handleNewOrder(Payload payload){
        System.out.println("Listener is called"+payload.getMessage());

    }



}
