package com.sudagoarth.trendhunter.repository;

import java.util.List;
import java.util.UUID;

public interface EmbeddingRepository {
    void upsert(UUID productId, float[] vector);
    /** Return most similar IDs ordered by similarity (best first). */
    List<UUID> similar(float[] query, int limit);
}
