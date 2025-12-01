package com.remediar.pix_microservice.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "donations")
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String pixTransactionId;

    @Column(nullable = false)
    private LocalDateTime donationDate;

    @Column(nullable = false)
    private String status; // PENDING, COMPLETED, FAILED

    @Column
    private String paymentMethod; // PIX, CREDIT_CARD, etc.

    @Column
    private String campaignId; // Se a doação foi para uma campanha específica

    @Column
    private String campaignName; // Nome da campanha

    @PrePersist
    protected void onCreate() {
        donationDate = LocalDateTime.now();
    }
} 