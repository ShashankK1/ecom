package com.ecom.ecom.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ecom.ecom.model.DiscountCode;
import com.ecom.ecom.model.Order;
import com.ecom.ecom.repository.OrderRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CheckoutServiceTest {

    @Mock
    CartService cartService;

    @Mock
    DiscountService discountService;

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    CheckoutService checkoutService;

    @Test
    void shouldApplyDiscount() {

        when(cartService.getTotal()).thenReturn(1000.0);

        DiscountCode code = new DiscountCode("DISC10", 10, false, null);
        when(discountService.validate("DISC10")).thenReturn(code);

        Order order = checkoutService.checkout("DISC10");

        assertEquals(100.0, order.getDiscountAmount());
        assertEquals(900.0, order.getFinalAmount());
    }
}

