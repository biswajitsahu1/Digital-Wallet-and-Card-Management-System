package com.bank.card.card_service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

/**
 * Card entity representing stored card metadata.
 * Sensitive fields (PAN, CVV) are AES-GCM encrypted before persistence.
 * PAN is never stored in plaintext; Stripe tokenization is used for transactions.
 */
@Entity
@Table(
        name = "cards",
        indexes = {
                @Index(name = "idx_card_user", columnList = "userId"),
                @Index(name = "idx_card_stripe_token", columnList = "stripeToken")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Owner of the card (User ID from user-service).
     */
    @Column(nullable = false)
    private Long userId;

    /**
     * Encrypted Primary Account Number (PAN).
     * AES-GCM encryption at rest.
     */
    @Lob
    @Column(nullable = false)
    private byte[] encryptedPan;

    /**
     * Initialization vector (IV) for AES-GCM (needed for decryption).
     */
    @Column(nullable = false, length = 24)
    private String iv;

    /**
     * Encrypted CVV (AES-GCM).
     * Stored only if absolutely necessary (PCI DSS consideration).
     */
    @Lob
    private byte[] encryptedCvv;

    /**
     * Card expiration month (MM).
     */
    @Column(nullable = false, length = 2)
    private String expiryMonth;

    /**
     * Card expiration year (YYYY).
     */
    @Column(nullable = false, length = 4)
    private String expiryYear;

    /**
     * Stripe token generated from PAN, CVV.
     * Used for actual payments instead of raw card data.
     */
    @Column(nullable = false, unique = true, length = 128)
    private String stripeToken;

    /**
     * Masked card number for UI (e.g., **** **** **** 1234).
     */
    @Column(nullable = false, length = 20)
    private String maskedPan;

    /**
     * Card brand (Visa, Mastercard, Amex, etc.).
     */
    @Column(nullable = false, length = 20)
    private String brand;

    /**
     * Soft delete flag (card is inactive but retained for audit).
     */
    @Column(nullable = false)
    private boolean active = true;

    @CreationTimestamp
    @Column(updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;
}