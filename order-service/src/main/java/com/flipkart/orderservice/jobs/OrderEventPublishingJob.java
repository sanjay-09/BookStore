package com.flipkart.orderservice.jobs;


import com.flipkart.orderservice.Service.OrderEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventPublishingJob {
    private final OrderEventService orderEventService;


    @Scheduled(cron="${orders.publish-order-events-job-cron}")
   @SchedulerLock(name="publishOrderEvents")
  public void publishOrderEvents(){
       log.info("publish event is called-{}", Instant.now());
      this.orderEventService.publishOrderEvents();
   }
}
