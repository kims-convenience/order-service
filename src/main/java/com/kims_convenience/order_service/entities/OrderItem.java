package com.kims_convenience.order_service.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity(name = "order_item")
@Getter
@Setter
@NoArgsConstructor
public class OrderItem {

    @Id
    private String id = UUID.randomUUID().toString();

    @Column(name = "product_id")
    private String productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "sku_id")
    private String skuId;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    public OrderItem(String id, String productId, String productName, String skuId, int quantity, double price, Order order) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.skuId = skuId;
        this.quantity = quantity;
        this.price = price;
        this.order = order;
    }
}
