package com.sudagoarth.trendhunter.web;

import com.sudagoarth.trendhunter.ingest.ProductIngestionService;
import com.sudagoarth.trendhunter.entity.Product;
import com.sudagoarth.trendhunter.repository.ProductRepository;
import com.sudagoarth.trendhunter.service.SimilarityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ProductIngestionService ingestion;
    private final SimilarityService similarity;
    private final ProductRepository productRepo;

    /** Run ingestion now (returns number of inserted items). */
    @PostMapping("/ingest")
    public ResponseEntity<Integer> ingest(@RequestParam(defaultValue = "50") int perSource) {
        int count = ingestion.runOnce(perSource);
        return ResponseEntity.ok(count);
    }

    /** Rebuild embeddings for all products (simple, sequential). */
    @PostMapping("/reindex")
    public ResponseEntity<String> reindex() {
        List<Product> all = productRepo.findAll();
        int ok = 0, fail = 0;
        for (Product p : all) {
            try {
                similarity.indexProduct(p);
                ok++;
            } catch (Exception e) {
                fail++;
            }
        }
        return ResponseEntity.ok("reindexed ok=" + ok + " fail=" + fail);
    }

    /** Reindex a specific product id. */
    @PostMapping("/reindex/{id}")
    public ResponseEntity<String> reindexOne(@PathVariable UUID id) {
        Product p = productRepo.findById(id).orElseThrow();
        similarity.indexProduct(p);
        return ResponseEntity.ok("ok");
    }
}
