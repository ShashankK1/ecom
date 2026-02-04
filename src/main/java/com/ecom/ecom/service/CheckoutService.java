package com.ecom.ecom.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.ecom.ecom.model.DiscountCode;
import com.ecom.ecom.model.Order;
import com.ecom.ecom.repository.OrderRepository;

@Service
@RequiredArgsConstructor
public class CheckoutService {

    private final CartService cartService;
    private final DiscountService discountService;
    private final OrderRepository orderRepository;

    public Order checkout(String couponCode) {

        double total = cartService.getTotal();
        int totalItems = cartService.getItems()
            .stream()
            .mapToInt(i -> i.getQuantity())
            .sum();
        double discount = 0;

        if (couponCode != null && !couponCode.isBlank()) {
            DiscountCode code = discountService.validate(couponCode);
            discount = total * code.getPercentage() / 100;
            discountService.markUsed(code);
        }

        Order order = new Order();
        order.setTotalAmount(total);
        order.setDiscountAmount(discount);
        order.setFinalAmount(total - discount);
            order.setTotalItems(totalItems);
        order.setCouponCode(couponCode);

        orderRepository.save(order);

        cartService.clearCart();
        discountService.generateIfEligible();

        return order;
    }
}

