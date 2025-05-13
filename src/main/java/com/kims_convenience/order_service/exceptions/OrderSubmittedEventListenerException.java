package com.kims_convenience.order_service.exceptions;

public class OrderSubmittedEventListenerException extends RuntimeException {

    public OrderSubmittedEventListenerException(Exception e, Object payload) {
        super("Error : " + e.getMessage() + " | Payload : " + payload);
    }
}
