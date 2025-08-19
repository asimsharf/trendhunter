package com.sudagoarth.trendhunter.entity;


import jakarta.persistence.*;
import lombok.*;


import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;


@Entity
@Table(name = "offers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Offer {


    @Id
    @GeneratedValue
    private UUID id;


    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;


    private String source; // AMAZON_SA, NOON, etc.
    @Column(columnDefinition = "text")
    private String url;


    private BigDecimal price;
    private String currency;
    private boolean inStock;
    private Instant lastSeenAt;
}