package com.kims_convenience.order_service.repositories;

import com.kims_convenience.order_service.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface OrderRepository extends JpaRepository<Order, String> {
}
