package com.ecom.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.ecom.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

