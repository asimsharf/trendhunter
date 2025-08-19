package com.sudagoarth.trendhunter.service;

import com.sudagoarth.trendhunter.entity.Offer;
import com.sudagoarth.trendhunter.entity.Product;
import com.sudagoarth.trendhunter.repository.OfferRepository;
import com.sudagoarth.trendhunter.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrendingService {

    private final ProductRepository products;
    private final OfferRepository offers;

    public List<Map<String, Object>> trending(String category, int limit) {
        // naive ranking: newest products + lowest price + inStock
        var all = products.findAll().stream().filter(p -> category == null || category.isBlank() || category.equalsIgnoreCase(p.getCategory())).collect(Collectors.toList());

        List<Map<String, Object>> out = new ArrayList<>();
        for (Product p : all) {
            List<Offer> pOffers = offers.findAll().stream().filter(o -> o.getProduct().getId().equals(p.getId()) && o.isInStock()).collect(Collectors.toList());
            if (pOffers.isEmpty()) continue;

            BigDecimal min = pOffers.stream().map(Offer::getPrice).min(Comparator.naturalOrder()).orElse(null);
            String cheapestSource = pOffers.stream().min(Comparator.comparing(Offer::getPrice)).map(Offer::getSource).orElse("NA");
            Instant newest = p.getCreatedAt();

            Map<String, Object> row = new LinkedHashMap<>();
            row.put("productId", p.getId());
            row.put("title", Optional.ofNullable(p.getTitleNorm()).orElse(p.getTitleRaw()));
            row.put("brand", p.getBrand());
            row.put("category", p.getCategory());
            row.put("cheapestPrice", min);
            row.put("cheapestSource", cheapestSource);
            row.put("createdAt", newest);
            out.add(row);
        }

        // simple score: newest first, then cheapest
        out.sort(Comparator.<Map<String, Object>, Instant>comparing(m -> (Instant) m.get("createdAt"), Comparator.nullsLast(Comparator.reverseOrder())).thenComparing(m -> (BigDecimal) m.get("cheapestPrice"), Comparator.nullsLast(Comparator.naturalOrder())));

        return out.stream().limit(limit).collect(Collectors.toList());
    }
}
