package com.kims_convenience.order_service.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class Order {

    @Id
    private String orderId;

    @Column(name = "customer_id", nullable = false)
    private String customerId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private PaymentInstrument paymentInstrument;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Address address;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;
}
