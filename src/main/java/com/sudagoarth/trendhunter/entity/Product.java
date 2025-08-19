package com.sudagoarth.trendhunter.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;


import java.time.Instant;
import java.util.UUID;


@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {


    @Id
    @GeneratedValue
    private UUID id;


    @Column(columnDefinition = "text")
    private String titleRaw;


    @Column(columnDefinition = "text")
    private String titleNorm;


    private String brand;
    private String model;
    private String category;


    @CreationTimestamp
    private Instant createdAt;
}