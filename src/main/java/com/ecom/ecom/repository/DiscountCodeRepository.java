package com.ecom.ecom.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.ecom.model.DiscountCode;

public interface DiscountCodeRepository extends JpaRepository<DiscountCode, String> {
}

