package com.flipkart.orderservice.Controller;


import com.flipkart.orderservice.Configuration.ApplicationConfiguration;
import com.flipkart.orderservice.Dto.Payload;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test-msg")
@RequiredArgsConstructor
public class RabbitMqTestController {
    private final RabbitTemplate rabbitTemplate;
    private final ApplicationConfiguration applicationConfiguration;


    @PostMapping
    public void sendMessage(@RequestBody Payload payload){
        rabbitTemplate.convertAndSend(applicationConfiguration.getOrderEventsExchange(),applicationConfiguration.getNewOrdersQueue(),payload);


    }
}
