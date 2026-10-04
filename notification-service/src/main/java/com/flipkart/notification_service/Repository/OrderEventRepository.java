package com.flipkart.notification_service.Repository;


import com.flipkart.notification_service.Model.OrderEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderEventRepository extends JpaRepository<OrderEvent,Long> {
    boolean existsByEventId(String eventId);
}
