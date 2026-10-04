package com.flipkart.orderservice.Repository.Projection;

import com.flipkart.orderservice.Model.Helper.Enum.OrderStatus;

public interface OrderSummary {
    String getOrderNumber();
    OrderStatus getStatus();
}
