package com.sudagoarth.trendhunter.ingest;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class MockTrendingClient implements TrendingSourceClient {

    @Override public String source() { return "MOCK"; }

    @Override
    public List<ExternalProduct> fetchTrending(int max) {
        var rnd = ThreadLocalRandom.current();
        return List.of(
            ExternalProduct.builder()
                .source(source())
                .externalId("MX-" + rnd.nextInt(1_000_000))
                .title("Nike Air Max 270 Men's Running Shoes")
                .brand("Nike").category("Shoes").currency("SAR")
                .priceCents(64900L).popularity(91)
                .url("https://example.com/nike-air-max-270")
                .imageUrl("https://picsum.photos/seed/airmax/600/600")
                .build(),
            ExternalProduct.builder()
                .source(source())
                .externalId("AP-" + rnd.nextInt(1_000_000))
                .title("Apple AirPods Pro (2nd gen) USB-C")
                .brand("Apple").category("Audio").currency("SAR")
                .priceCents(99900L).popularity(88)
                .url("https://example.com/airpods-pro-2")
                .imageUrl("https://picsum.photos/seed/airpods/600/600")
                .build()
        );
    }
}
