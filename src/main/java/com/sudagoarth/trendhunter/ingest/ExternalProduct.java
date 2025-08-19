package com.sudagoarth.trendhunter.ingest;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ExternalProduct {
    String source;
    String externalId;
    String title;
    String url;
    String imageUrl;
    String brand;
    String category;
    Long   priceCents;
    String currency;
    Integer popularity;
}
