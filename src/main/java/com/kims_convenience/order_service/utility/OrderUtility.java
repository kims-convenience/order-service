package com.kims_convenience.order_service.utility;

import com.kims_convenience.order_service.dto.order_event.AddressDto;
import com.kims_convenience.order_service.dto.order_event.OrderDto;
import com.kims_convenience.order_service.dto.order_event.OrderSubmittedEvent;
import com.kims_convenience.order_service.dto.order_event.PaymentInstrumentDto;
import com.kims_convenience.order_service.entities.Address;
import com.kims_convenience.order_service.entities.Order;
import com.kims_convenience.order_service.entities.OrderItem;
import com.kims_convenience.order_service.entities.PaymentInstrument;

import java.util.List;
import java.util.stream.Collectors;

public class OrderUtility {

    public static Order getOrder(OrderSubmittedEvent event) {
        Order order = new Order();
        order.setOrderId(event.getOrder().getOrderId());
        order.setCustomerId(event.getOrder().getCustomerId());
        order.setOrderItems(getOrderItems(event.getOrder(), order));
        order.setPaymentInstrument(getPaymentInstrument(event.getOrder(), order));
        order.setAddress(getAddress(event.getOrder(), order));
        return order;
    }

    private static List<OrderItem> getOrderItems(OrderDto orderDto, Order order) {
        return orderDto.getCart().getLineItems()
                .stream()
                .map(li -> new OrderItem(
                        li.getLineItemId(),
                        li.getProductId(),
                        li.getProductName(),
                        li.getSkuId(),
                        li.getQuantity(),
                        li.getPrice(),
                        order))
                .collect(Collectors.toList());
    }

    private static PaymentInstrument getPaymentInstrument(OrderDto orderDto, Order order) {
        PaymentInstrumentDto paymentInstrumentDto = orderDto.getPaymentInstrument();
        return new PaymentInstrument(
                paymentInstrumentDto.getPaymentInstrumentId(),
                paymentInstrumentDto.getPaymentMethodType(),
                paymentInstrumentDto.getProvider(),
                paymentInstrumentDto.getMaskedCardNumber(),
                paymentInstrumentDto.getCardHolderName(),
                paymentInstrumentDto.getExpiryMonth(),
                paymentInstrumentDto.getExpiryYear(),
                paymentInstrumentDto.getLastUsedOn(),
                paymentInstrumentDto.getInstrumentToken(),
                order
        );
    }

    private static Address getAddress(OrderDto orderDto, Order order) {
        AddressDto addressDto = orderDto.getAddress();
        return new Address(
                addressDto.getAddressId(),
                addressDto.getName(),
                addressDto.getPhoneNumber(),
                addressDto.getAddressLine1(),
                addressDto.getAddressLine2(),
                addressDto.getCity(),
                addressDto.getState(),
                addressDto.getPostalCode(),
                addressDto.getCountry(),
                addressDto.getAddressType(),
                addressDto.isDefaultAddress(),
                order
        );
    }
}
