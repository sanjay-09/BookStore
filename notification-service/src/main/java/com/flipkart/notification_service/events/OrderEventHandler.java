package com.flipkart.notification_service.events;


import com.flipkart.notification_service.Dto.OrderCreatedEvent;
import com.flipkart.notification_service.Model.OrderEvent;
import com.flipkart.notification_service.Repository.OrderEventRepository;
import com.flipkart.notification_service.Service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventHandler {
    private final NotificationService notificationService;
    private final OrderEventRepository orderEventRepository;

    @RabbitListener(queues = "${notification.new-orders-queue}")
    void handleCreateCreatedEvent(OrderCreatedEvent event){
        System.out.println("order created event"+event.getEventId());
        if (orderEventRepository.existsByEventId(event.getEventId())){
            log.warn("Received duplicate Order Created event Id:"+event.getEventId());
            return;
        }
        notificationService.sendOrderCreatedNotification(event);
        OrderEvent orderEvent=OrderEvent.builder().eventId(event.getEventId()).updatedAt(LocalDateTime.now()).build();
        this.orderEventRepository.save(orderEvent);
    }

    @RabbitListener(queues = "${notification.delivered-orders-queue}")
    void handleDeliveredOrderEvent(OrderCreatedEvent event){
        System.out.println("order delivered event"+event.getEventId());
        if (orderEventRepository.existsByEventId(event.getEventId())){
            log.warn("Received duplicate delivered  Created event Id:"+event.getEventId());
            return;
        }
        notificationService.sendOrderDeliveredNotification(event);
        OrderEvent orderEvent=OrderEvent.builder().eventId(event.getEventId()).updatedAt(LocalDateTime.now()).build();
        this.orderEventRepository.save(orderEvent);
    }
}
