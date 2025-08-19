package com.sudagoarth.trendhunter.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "products",
        indexes = {
                @Index(name = "ix_products_title", columnList = "titleRaw"),
                @Index(name = "ix_products_brand", columnList = "brand"),
                @Index(name = "ix_products_category", columnList = "category")
        })
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Product {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, length = 2048)
    private String titleRaw;

    @Column(length = 2048)
    private String titleNormalized;  // <-- use this name everywhere

    @Column(length = 64)
    private String source;

    @Column(length = 128)
    private String externalId;

    @Column(columnDefinition = "text")
    private String url;

    @Column(columnDefinition = "text")
    private String imageUrl;

    @Column(length = 128)
    private String brand;

    @Column(length = 128)
    private String category;

    private Long priceCents;

    @Column(length = 8)
    private String currency;

    private Integer popularity;

    // NEW: timestamp
    @Column(nullable = false, updatable = false)
    private java.time.Instant createdAt;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = java.time.Instant.now();
    }
}
