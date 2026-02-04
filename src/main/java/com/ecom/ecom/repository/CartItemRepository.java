package com.ecom.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.ecom.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
