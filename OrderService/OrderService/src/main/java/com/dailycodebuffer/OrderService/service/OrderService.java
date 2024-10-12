package com.dailycodebuffer.OrderService.service;

import com.dailycodebuffer.OrderService.model.OrderRequest;
import org.springframework.stereotype.Repository;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);
}
