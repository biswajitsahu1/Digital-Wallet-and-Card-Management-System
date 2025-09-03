package com.bank.card.card_service.util;

import com.bank.card.card_service.dto.CardAddResponse;
import com.bank.card.card_service.dto.CardDto;
import com.bank.card.card_service.entity.Card;

public class Mapper {
    public CardAddResponse toResponse(Card card) {
        return CardAddResponse.builder()
                .id(card.getId())
                .brand(card.getBrand())
                .maskedPan(card.getMaskedPan())
                .expiryMonth(card.getExpiryMonth())
                .expiryYear(card.getExpiryYear())
                .stripeToken(card.getStripeToken())
                .active(card.isActive())
                .build();
    }

    public Card toEntity(CardDto cardDto) {
        return Card.builder()
                .id(cardDto.getId())
                .userId(cardDto.getUserId())
                .encryptedPan(cardDto.getEncryptedPan().getBytes())
                .iv(cardDto.getIv())
                .encryptedCvv(cardDto.getEncryptedCvv().getBytes())
                .expiryMonth(cardDto.getExpiryMonth())
                .expiryYear(cardDto.getExpiryYear())
                .stripeToken(cardDto.getStripeToken())
                .maskedPan(cardDto.getMaskedPan())
                .brand(cardDto.getBrand())
                .active(cardDto.isActive())
                .build();
    }

    public CardDto toDto(Card card) {
        return CardDto.builder()
                .id(card.getId())
                .userId(card.getUserId())
                .encryptedPan(new String(card.getEncryptedPan()))
                .iv(card.getIv())
                .encryptedCvv(new String(card.getEncryptedCvv()))
                .expiryMonth(card.getExpiryMonth())
                .expiryYear(card.getExpiryYear())
                .stripeToken(card.getStripeToken())
                .maskedPan(card.getMaskedPan())
                .brand(card.getBrand())
                .active(card.isActive())
                .build();
    }

}
