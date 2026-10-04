package com.flipkart.orderservice.Publisher;

import com.flipkart.orderservice.Configuration.ApplicationConfiguration;
import com.flipkart.orderservice.Dto.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventPublisher {
    private final RabbitTemplate rabbitTemplate;
    private final ApplicationConfiguration applicationConfiguration;


    public void publish(OrderCreatedEvent event){
        this.send(applicationConfiguration.getNewOrdersQueue(),event);

    }

    public void publishForCancelled(OrderCreatedEvent event){
        this.send(applicationConfiguration.getCancelledOrdersQueue(),event);

    }
    public void publishForDelivered(OrderCreatedEvent event){
        this.send(applicationConfiguration.getDeliveredOrdersQueue(),event);

    }

    private void send(String routingKey,Object payload){
        rabbitTemplate.convertAndSend(applicationConfiguration.getOrderEventsExchange(),routingKey,payload);
    }



}
