package com.ecom.ecom.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.ecom.ecom.model.Order;
import com.ecom.ecom.service.CheckoutService;

@RestController
@RequestMapping("/api/checkout")
@RequiredArgsConstructor
public class CheckoutController {

    private final CheckoutService checkoutService;

    @PostMapping
    public Order checkout(@RequestParam(required = false) String couponCode) {
        return checkoutService.checkout(couponCode);
    }
}

