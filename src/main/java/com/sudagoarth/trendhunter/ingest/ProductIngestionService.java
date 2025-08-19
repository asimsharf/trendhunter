package com.sudagoarth.trendhunter.ingest;

import com.sudagoarth.trendhunter.entity.Product;
import com.sudagoarth.trendhunter.repository.ProductRepository;
import com.sudagoarth.trendhunter.service.SimilarityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductIngestionService {

    private final List<TrendingSourceClient> clients;
    private final ProductRepository productRepo;
    private final SimilarityService similarity;

    /** Pulls from each client hourly; can be triggered manually too. */
    @Transactional
    public int runOnce(int perSource) {
        int inserted = 0;
        for (TrendingSourceClient client : clients) {
            var items = client.fetchTrending(perSource);
            for (ExternalProduct ep : items) {
                var existing = productRepo.findBySourceAndExternalId(ep.getSource(), ep.getExternalId());
                if (existing.isPresent()) {
                    continue; // skip duplicates
                }
                var p = Product.builder()
                        .titleRaw(ep.getTitle())
                        .titleNormalized(null)
                        .source(ep.getSource())
                        .externalId(ep.getExternalId())
                        .url(ep.getUrl())
                        .imageUrl(ep.getImageUrl())
                        .brand(ep.getBrand())
                        .category(ep.getCategory())
                        .priceCents(ep.getPriceCents())
                        .currency(ep.getCurrency())
                        .popularity(ep.getPopularity())
                        .build();

                var saved = productRepo.save(p);
                // index embedding (async inside service or here sequentially)
                try {
                    similarity.indexProduct(saved);
                } catch (Exception e) {
                    log.warn("Embedding failed for product {}: {}", saved.getId(), e.getMessage());
                }
                inserted++;
            }
            log.info("Ingested {} items from {}", items.size(), client.source());
        }
        return inserted;
    }

    // every hour at minute 7
    @Scheduled(cron = "0 7 * * * *")
    public void scheduledIngestion() {
        int n = runOnce(50);
        log.info("Scheduled ingestion finished: {} new products", n);
    }
}
