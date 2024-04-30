package com.order.orderservice.services;

import com.order.orderservice.dto.OrderRequest;

public interface OrderService {

    void placeOrder(OrderRequest orderRequest);
}
