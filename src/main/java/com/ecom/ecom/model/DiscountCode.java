package com.ecom.ecom.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiscountCode {

    @Id
    private String code;

    private int percentage;
    private boolean used;

    private LocalDateTime issuedAt = LocalDateTime.now();
}
