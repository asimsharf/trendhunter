package com.sudagoarth.trendhunter.repository;

import com.pgvector.PGvector;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class EmbeddingRepository {
    private final JdbcTemplate jdbc;

    public void upsert(UUID productId, float[] vec) {
        PGvector v = new PGvector(vec);
        jdbc.update("""
                INSERT INTO product_embedding (product_id, embedding)
                VALUES (?, ?)
                ON CONFLICT (product_id) DO UPDATE SET embedding = EXCLUDED.embedding
                """, productId, v);
    }

    public List<UUID> similar(float[] query, int limit) {
        PGvector v = new PGvector(query);
        return jdbc.query("""
                SELECT product_id
                FROM product_embedding
                ORDER BY embedding <=> ?
                LIMIT ?
                """, (rs, i) -> UUID.fromString(rs.getString("product_id")), v, limit);
    }
}
