package com.flipkart.notification_service.Configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitMQDeclarablesInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final RabbitAdmin rabbitAdmin;



    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        // This forces RabbitAdmin to initialize and declare all Queue, Exchange, and Binding beans
        rabbitAdmin.initialize();
    }
}
