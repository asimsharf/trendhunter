package com.sudagoarth.trendhunter.service.dto;

import java.util.UUID;

public record SimilarProductDto(
        UUID id,
        String title,
        String brand,
        String category,
        String imageUrl,
        double score   // optional; 1 - cosine distance etc.
) {}
