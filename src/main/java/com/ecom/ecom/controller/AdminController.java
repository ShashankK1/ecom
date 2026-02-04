package com.ecom.ecom.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.ecom.ecom.repository.OrderRepository;
import com.ecom.ecom.service.CartService;
import com.ecom.ecom.service.DiscountService;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final CartService cartService;
    private final OrderRepository orderRepo;
    private final DiscountService discountService;

    @GetMapping("/metrics")
    public Map<String, Object> metrics() {
        double revenue = orderRepo.findAll()
                .stream()
                .mapToDouble(o -> o.getFinalAmount())
                .sum();

        double discountGiven = orderRepo.findAll()
                .stream()
                .mapToDouble(o -> o.getDiscountAmount())
                .sum();

        return Map.of(
                "itemsPurchased", cartService.getTotalItemsPurchased(),
                "revenue", revenue,
                "discountCodesGenerated", discountService.countGenerated(),
                "totalDiscountGiven", discountGiven
        );
    }
}

