package com.ecom.ecom.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.ecom.ecom.model.CartItem;
import com.ecom.ecom.repository.CartItemRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;

    public CartItem addItem(CartItem item) {
        return cartItemRepository.save(item);
    }

    public List<CartItem> getItems() {
        return cartItemRepository.findAll();
    }

    public double getTotal() {
        return cartItemRepository.findAll()
                .stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();
    }

    public int getTotalItemsPurchased() {
        return cartItemRepository.findAll()
                .stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }

    public void clearCart() {
        cartItemRepository.deleteAll();
    }
}

