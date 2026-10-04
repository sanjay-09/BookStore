package com.flipkart.orderservice.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.flipkart.orderservice.Dto.OrderCreatedEvent;
import com.flipkart.orderservice.Model.Helper.Enum.OrderEventType;
import com.flipkart.orderservice.Model.OrderEvent;
import com.flipkart.orderservice.Publisher.OrderEventPublisher;
import com.flipkart.orderservice.Repository.OrderEventRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OrderEventService {
    private final OrderEventRepository orderEventRepository;
    private final ObjectMapper objectMapper;
    private final OrderEventPublisher eventPublisher;

    public void save(OrderCreatedEvent orderCreatedEvent, OrderEventType eventType) {

        OrderEvent orderEvent = OrderEvent.builder()
                .eventId(orderCreatedEvent.getEventId())
                .orderNumber(orderCreatedEvent.getOrderNumber())
                .eventType(eventType)
                .createdAt(orderCreatedEvent.getCreatedAt())
                .payload(toJsonPayload(orderCreatedEvent))
                .build();

        this.orderEventRepository.save(orderEvent);
    }

    private  String toJsonPayload(OrderCreatedEvent event){
        try{
            return this.objectMapper.writeValueAsString(event);
        }
        catch (Exception e){
            log.info("error while parsing the json");
            throw new RuntimeException(e);


        }

    }

    public void  publishOrderEvents(){
        Sort sort=Sort.by("createdAt").ascending();
        List<OrderEvent> events=orderEventRepository.findAll(sort);
        log.info("Found {} Order Events to be published",events.size());
        for(OrderEvent event:events){
            this.publishEvent(event);
            this.orderEventRepository.delete(event);


        }


    }
    private void publishEvent(OrderEvent event){
        OrderEventType eventType=event.getEventType();

        switch (eventType){
            case ORDER_CREATED:
                OrderCreatedEvent orderCreatedEvent=fromJsonPayLoad(event.getPayload(),OrderCreatedEvent.class);
                this.eventPublisher.publish(orderCreatedEvent);
                break;

            case ORDER_CANCELLED:
                OrderCreatedEvent orderCancelledEvent=fromJsonPayLoad(event.getPayload(),OrderCreatedEvent.class);
                this.eventPublisher.publishForCancelled(orderCancelledEvent);
                break;

            case ORDER_DELIVERED:
                OrderCreatedEvent orderDeliveredEvent=fromJsonPayLoad(event.getPayload(),OrderCreatedEvent.class);
                this.eventPublisher.publishForDelivered(orderDeliveredEvent);
                break;

            default:
                log.warn("Unsupported OrderEventType:{}",eventType);

        }



    }

    private <T> T fromJsonPayLoad(String event,Class<T> type){
        try{
           return this.objectMapper.readValue(event,type);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }


    }
}
