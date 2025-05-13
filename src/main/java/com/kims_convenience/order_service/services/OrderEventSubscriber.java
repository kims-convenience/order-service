package com.kims_convenience.order_service.services;

import com.kims_convenience.order_service.dto.order_event.OrderSubmittedEvent;
import com.kims_convenience.order_service.exceptions.OrderSubmittedEventListenerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventSubscriber {

    private static final Logger logger = LoggerFactory.getLogger(OrderEventSubscriber.class);

    @Autowired
    private OrderService orderService;

    @KafkaListener(
            topics = "order.submitted",
            groupId = "order-service-group",
            containerFactory = "orderSubmittedKafkaListenerContainerFactory"
    )
    public void handleOrderSubmitted(OrderSubmittedEvent payload) {
        try {
            logger.info("[handleOrderSubmitted] Received OrderSubmittedEvent: {}", payload.toLogString());
            orderService.handleNewOrderEvent(payload);
        } catch (Exception e) {
            logger.error("[handleOrderSubmitted] Failed to parse OrderSubmittedEvent", e);
            throw new OrderSubmittedEventListenerException(e, payload);
        }
    }
}
