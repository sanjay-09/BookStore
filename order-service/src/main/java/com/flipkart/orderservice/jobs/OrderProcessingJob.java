package com.flipkart.orderservice.jobs;

import com.flipkart.orderservice.Service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderProcessingJob {
    private final OrderService orderService;

//    @Scheduled(cron="${orders.new-orders-job-cron}")
//    @SchedulerLock(name="processNewOrders")
//    public void processNewOrders(){
//        log.info("Processing new orders at {}", Instant.now());
//        orderService.processNewOrders();
//    }
}
