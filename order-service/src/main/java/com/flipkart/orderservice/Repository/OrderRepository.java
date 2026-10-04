package com.flipkart.orderservice.Repository;

import com.flipkart.orderservice.Model.Helper.Enum.OrderStatus;
import com.flipkart.orderservice.Model.Order;
import com.flipkart.orderservice.Repository.Projection.OrderSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByStatus(OrderStatus orderStatus);


    @Query("""
            select o.orderNumber AS orderNumber,o.status as status from Order o where o.userName=:userName
            """)
    List<OrderSummary> findByUserName(String userName);


    @Query("""
            select o from Order o
            JOIN FETCH o.items
            where o.userName=:userName and o.orderNumber=:orderNumber
            """
    )
    Optional<Order> findByUserNameAndOrderNumber(String userName,String orderNumber);
}
