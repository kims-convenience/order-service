package com.kims_convenience.order_service.services;

import com.kims_convenience.order_service.dto.order_event.OrderSubmittedEvent;
import com.kims_convenience.order_service.entities.Order;
import com.kims_convenience.order_service.repositories.OrderRepository;
import com.kims_convenience.order_service.utility.OrderUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    public void handleNewOrderEvent(OrderSubmittedEvent event) {
        logger.info("[handleNewOrderEvent] OrderSubmittedEvent = {}", event.toLogString());
        Order order = OrderUtility.getOrder(event);
        orderRepository.save(order);
    }
}
