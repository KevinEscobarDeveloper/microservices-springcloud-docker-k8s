package com.dailycodebuffer.OrderService.service;

import com.dailycodebuffer.OrderService.entity.Order;
import com.dailycodebuffer.OrderService.model.OrderRequest;
import com.dailycodebuffer.OrderService.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements  OrderService{
    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    @Override
    public long placeOrder(OrderRequest orderRequest) {
        //Order Entity -> Save the data with Status Order Created
        //Product Service - Block Products (Reduce the Quantity)
        //Payment Service -> Payments -> Success -> COMPLETE, Else
        //CANCELLED

        log.info("Placing Order Request: {}", orderRequest);

        Order order = Order.builder()
                .amount(orderRequest.getTotalAmount())
                .orderStatus(("CREATED"))
                .productId(orderRequest.getProductId())
                .orderDate(Instant.now())
                .quantity(orderRequest.getQuantity())
                .build();

        order = orderRepository.save(order);
        log.info("Order Places succesfully with Order Id: {} ", order.getId());
        return order.getId();
    }
}
