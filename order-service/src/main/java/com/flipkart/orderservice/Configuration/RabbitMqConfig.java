package com.flipkart.orderservice.Configuration;


import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.ObjectMapper;

@Configuration
@RequiredArgsConstructor
public class RabbitMqConfig {
    private final ApplicationConfiguration applicationConfiguration;

    @Bean
    DirectExchange exchange(){
        return  new DirectExchange(applicationConfiguration.getOrderEventsExchange());
    }

    @Bean
    Queue newOrdersQueue(){
        return QueueBuilder.durable(applicationConfiguration.getNewOrdersQueue()).build();

    }

    @Bean
    Binding newOrdersQueueBinding(){
        return BindingBuilder.bind(newOrdersQueue()).to(exchange()).with(applicationConfiguration.getNewOrdersQueue());
    }

    @Bean
    Queue deliveredOrderedQueue(){
        return QueueBuilder.durable(applicationConfiguration.getDeliveredOrdersQueue()).build();
    }

    @Bean
    Binding deliveredQueueBinding(){
        return BindingBuilder.bind(deliveredOrderedQueue()).to(exchange()).with(applicationConfiguration.getDeliveredOrdersQueue());
    }

    @Bean
    Queue cancelledOrderedQueue(){
        return QueueBuilder.durable(applicationConfiguration.getCancelledOrdersQueue()).build();

    }

    @Bean
    Binding cancelledOrdersQueueBinding(){
        return BindingBuilder.bind(cancelledOrderedQueue()).to(exchange()).with(applicationConfiguration.getCancelledOrdersQueue());
    }

    @Bean
    Queue errorOrdersQueue(){
        return QueueBuilder.durable(applicationConfiguration.getErrorsOrdersQueue()).build();
    }

    @Bean
    Binding errorOrderQueueBinding(){
        return BindingBuilder.bind(errorOrdersQueue()).to(exchange()).with(applicationConfiguration.getErrorsOrdersQueue());
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate=new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new JacksonJsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory) {

        SimpleRabbitListenerContainerFactory factory =
                new SimpleRabbitListenerContainerFactory();

        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(
                new JacksonJsonMessageConverter()
        );

        return factory;
    }
//    @Bean
//    CommandLineRunner checkRabbit(AmqpAdmin amqpAdmin) {
//        return args -> {
//            System.out.println("RabbitAdmin = " + amqpAdmin);
//
//            RabbitAdmin rabbitAdmin = (RabbitAdmin) amqpAdmin;
//
//            rabbitAdmin.initialize();
//
//            System.out.println(
//                    "new-orders = " +
//                            amqpAdmin.getQueueProperties("new-orders")
//            );
//        };
//    }
    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }
}


