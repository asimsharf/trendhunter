package com.sudagoarth.trendhunter.jobs;

import com.sudagoarth.trendhunter.entity.Offer;
import com.sudagoarth.trendhunter.entity.Product;
import com.sudagoarth.trendhunter.repository.OfferRepository;
import com.sudagoarth.trendhunter.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Random;

@Component
@EnableScheduling
@RequiredArgsConstructor
@Profile("dev") // only runs when SPRING_PROFILES_ACTIVE=dev
public class MockCollector {

    private final ProductRepository products;
    private final OfferRepository offers;
    private final Random r = new Random();

    @Scheduled(initialDelay = 2000, fixedDelay = 60_000)
    public void seed() {
        if (products.count() > 20) return;

        Product p = Product.builder().titleRaw("Wireless Earbuds " + System.currentTimeMillis()).titleNorm("سماعات لاسلكية").brand("Acme").model("X" + r.nextInt(999)).category("electronics").build();
        products.save(p);

        offers.save(Offer.builder().product(p).source("NOON").url("https://example.noon/" + p.getId()).price(BigDecimal.valueOf(79 + r.nextInt(50))).currency("SAR").inStock(true).lastSeenAt(Instant.now()).build());

        offers.save(Offer.builder().product(p).source("AMAZON_SA").url("https://amazon.sa/dp/" + p.getId()).price(BigDecimal.valueOf(69 + r.nextInt(40))).currency("SAR").inStock(true).lastSeenAt(Instant.now()).build());
    }
}
