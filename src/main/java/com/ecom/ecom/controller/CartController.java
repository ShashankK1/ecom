package com.ecom.ecom.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.ecom.ecom.model.CartItem;
import com.ecom.ecom.service.CartService;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/items")
    public CartItem addItem(@RequestBody CartItem item) {
        return cartService.addItem(item);
    }
}

