package com.bank.card.card_service.dto;

import lombok.*;
import jakarta.validation.constraints.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
class CardAddRequest {
    @NotBlank
    @Size(min = 13, max = 19, message = "PAN must be 13–19 digits")
    private String pan;

    @NotNull(message = "Expiration month is required")
    @Min(1) @Max(12)
    private Integer expMonth;

    @NotNull(message = "Expiration year is required")
    @Min(2024) @Max(2100)
    private Integer expYear;

    @NotBlank(message = "CVV is required")
    @Size(min = 3, max = 4)
    private String cvv;

    @NotBlank(message = "Name on card is required")
    private String nameOnCard;
}