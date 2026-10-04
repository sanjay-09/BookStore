package com.flipkart.orderservice.Configuration;

import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQDeclarablesInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final RabbitAdmin rabbitAdmin;

    public RabbitMQDeclarablesInitializer(RabbitAdmin rabbitAdmin) {
        this.rabbitAdmin = rabbitAdmin;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        // This forces RabbitAdmin to initialize and declare all Queue, Exchange, and Binding beans
        rabbitAdmin.initialize();
    }
}
