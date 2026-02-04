package com.ecom.ecom.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ecom.ecom.model.CartItem;
import com.ecom.ecom.model.DiscountCode;
import com.ecom.ecom.model.Order;
import com.ecom.ecom.repository.OrderRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

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

        CartItem item = new CartItem(1L, "P1", 500, 2);
        when(cartService.getItems()).thenReturn(List.of(item));

        DiscountCode code = new DiscountCode("DISC10", 10, false, null);
        when(discountService.validate("DISC10")).thenReturn(code);

        Order order = checkoutService.checkout("DISC10");

        assertEquals(100.0, order.getDiscountAmount());
        assertEquals(900.0, order.getFinalAmount());
        assertEquals(2, order.getTotalItems());
    }

    @Test
    void shouldCheckoutWithoutCoupon() {

        when(cartService.getTotal()).thenReturn(500.0);
        when(cartService.getItems()).thenReturn(
                List.of(new CartItem(1L, "P2", 250, 2))
        );

        Order order = checkoutService.checkout(null);

        assertEquals(0.0, order.getDiscountAmount());
        assertEquals(500.0, order.getFinalAmount());
        assertEquals(2, order.getTotalItems());

        verify(discountService, never()).validate(any());
        verify(discountService, never()).markUsed(any());
    }

    @Test
    void shouldThrowExceptionForInvalidCoupon() {

        when(cartService.getTotal()).thenReturn(800.0);
        when(cartService.getItems()).thenReturn(
                List.of(new CartItem(1L, "P3", 400, 2))
        );

        when(discountService.validate("BADCODE"))
                .thenThrow(new RuntimeException("Invalid coupon"));

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> checkoutService.checkout("BADCODE")
        );

        assertEquals("Invalid coupon", ex.getMessage());

        verify(orderRepository, never()).save(any());
        verify(cartService, never()).clearCart();
    }

    @Test
    void shouldFailWhenCouponAlreadyUsed() {

        when(cartService.getTotal()).thenReturn(600.0);
        when(cartService.getItems()).thenReturn(
                List.of(new CartItem(1L, "P4", 300, 2))
        );

        when(discountService.validate("DISC10"))
                .thenThrow(new RuntimeException("Coupon already used"));

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> checkoutService.checkout("DISC10")
        );

        assertEquals("Coupon already used", ex.getMessage());

        verify(orderRepository, never()).save(any());
        verify(cartService, never()).clearCart();
    }

    @Test
    void shouldCalculateTotalItemsFromMultipleCartItems() {

        when(cartService.getTotal()).thenReturn(1500.0);

        when(cartService.getItems()).thenReturn(List.of(
                new CartItem(1L, "P1", 500, 1),
                new CartItem(2L, "P2", 500, 2)
        ));

        Order order = checkoutService.checkout(null);

        assertEquals(3, order.getTotalItems());
        assertEquals(1500.0, order.getFinalAmount());
    }

}

