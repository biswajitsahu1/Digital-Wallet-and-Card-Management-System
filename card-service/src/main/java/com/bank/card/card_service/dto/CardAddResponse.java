package com.bank.card.card_service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardAddResponse {

    private Long id;
    private String brand;
    private String maskedPan;
    private String expiryMonth;
    private String expiryYear;
    private String stripeToken;
    private boolean active;
}