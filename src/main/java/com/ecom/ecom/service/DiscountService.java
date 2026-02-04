package com.ecom.ecom.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.ecom.ecom.model.DiscountCode;
import com.ecom.ecom.repository.DiscountCodeRepository;
import com.ecom.ecom.repository.OrderRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DiscountService {

    private static final int NTH_ORDER = 2;
    private static final int DISCOUNT_PERCENT = 10;

    private final DiscountCodeRepository discountRepo;
    private final OrderRepository orderRepo;

    public DiscountCode validate(String code) {
        DiscountCode discount = discountRepo.findById(code)
                .orElseThrow(() -> new RuntimeException("Invalid coupon"));

        if (discount.isUsed()) {
            throw new RuntimeException("Coupon already used");
        }
        return discount;
    }

    public void markUsed(DiscountCode discount) {
        discount.setUsed(true);
        discountRepo.save(discount);
    }

    public void generateIfEligible() {
        long orderCount = orderRepo.count();

        if (orderCount % NTH_ORDER == 0) {
            String code = "DISC-" + UUID.randomUUID().toString().substring(0, 6);
            discountRepo.save(new DiscountCode(code, DISCOUNT_PERCENT, false, null));
        }
    }

    public long countGenerated() {
        return discountRepo.count();
    }
}

