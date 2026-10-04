package com.flipkart.orderservice.Repository;

import com.flipkart.orderservice.Model.OrderEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderEventRepository extends JpaRepository<OrderEvent, Long> {
}
