package com.bank.card.card_service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardDto {

    private Long id;
    private Long userId;
    private String encryptedPan;
    private String iv;
    private String encryptedCvv;
    private String expiryMonth;
    private String expiryYear;
    private String stripeToken;
    private String maskedPan;
    private String brand;
    private boolean active;
}